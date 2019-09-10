package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.repo.ShoppingCartItemRepository;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    private final ShoppingCartItemRepository repository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartItemRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public UUID generateShoppingCartUniqueId() {
        return UUID.randomUUID();
    }


    /**
     * 7.2 ADD PRODUCT TO SHOPPING CART
     */
    @Transactional
    public ShoppingCartItem addProductToShoppingCart(UUID cartId, Product product, String productAttributes, Integer quantity) {

        // find if this product having same attributes already added
        List<ShoppingCartItem> existingItems = this.repository.findByCartIdAndProductId(cartId, product.getId());
        existingItems.removeIf( (x) -> !x.getAttributesStr().equals(productAttributes) );

        ShoppingCartItem item;

        if (existingItems.isEmpty()) {
            item = new ShoppingCartItem();
            item.setCartId(cartId);
            item.setProductId(product.getId());
            item.setQuantity(quantity);
            item.setAttributesStr(productAttributes); // todo: try to understand what attributes mean: probably coma-separated values?
            item.setBuyNow(false); // todo: Q: what is buyNow field?
            item.setAddedOn(Instant.now());
        }
        else {
            item = existingItems.get(0);
            item.setQuantity(item.getQuantity() + quantity);
        }

        return this.repository.saveAndFlush(item);
    }

    /**
     * 7.3 GET LIST OF PRODUCTS IN A SHOPPING CART
     */
    public List<ShoppingCartItem> fetchItemsOf(UUID cartId) {

        List<ShoppingCartItem> items = this.repository.findByCartId(cartId, Sort.by("id"));

        return items.stream().peek(
                (x)-> x.setProduct(
                    this.productService.findById(x.getProductId())
                        .orElseThrow(()-> new IllegalStateException("product " + x.getProductId() + " not found"))
                )
        ).collect(Collectors.toList());
    }


    public ShoppingCartItem findShoppingCartItemById(Long itemId) throws NotFoundException {

        ShoppingCartItem item = this.repository.findById(itemId).orElseThrow(
                () -> new NotFoundException(NotFoundException.CART_ITEM_NOT_FOUND)
        );

        Product product = this.productService.findById(item.getProductId()).orElseThrow(
                () -> new IllegalStateException("product " + item.getProductId() + " not found")
        );

        item.setProduct(product);
        return item;
    }


    /**
     * 7.4 UPDATE CART ITEM QUANTITY
     */
    public ShoppingCartItem updateItemQuantity(ShoppingCartItem item, Integer quantity) {
        item.setQuantity(quantity);
        return this.repository.saveAndFlush(item);
    }

    /**
     * 7.5 EMPTY SHOPPING CART
     */
    @Transactional
    public void emptyShoppingCart(UUID cartId) {
        this.repository.deleteByCartId(cartId);
    }


    public void removeItem(ShoppingCartItem item) {
        this.repository.deleteById(item.getId());
    }
}
