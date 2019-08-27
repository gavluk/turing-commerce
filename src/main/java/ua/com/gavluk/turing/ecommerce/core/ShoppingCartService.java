package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.repo.ShoppingCartItemRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShoppingCartService {

    private final ShoppingCartItemRepository repository;

    public ShoppingCartService(ShoppingCartItemRepository repository) {
        this.repository = repository;
    }

    public UUID generateShoppingCartUniqueId() {
        return UUID.randomUUID();
    }


    /**
     * 7.2 ADD PRODUCT TO SHOPPING CART
     */
    public ShoppingCartItem addProductToShoppingCart(UUID cartId, Product product, String productAttributes, Integer quantity) {
        ShoppingCartItem item = new ShoppingCartItem();
        item.setCartId(cartId);
        item.setProductId(product.getId());
        item.setQuantity(quantity);
        item.setAttributesStr(productAttributes); // todo: try to understand what attributes mean: probably coma-separated values?
        item.setBuyNow(false); // todo: Q: what is buyNow field?
        item.setAddedOn(Instant.now());
        return this.repository.save(item);
    }

    /**
     * 7.3 GET LIST OF PRODUCTS IN A SHOPPING CART
     */
    public List<ShoppingCartItem> fetchItemsOf(UUID cartId) {
        return this.fetchItemsOf(cartId);
    }


    public Optional<ShoppingCartItem> findSjoppingCartItemById(Long itemId) {
        return this.repository.findById(itemId);
    }


    /**
     * 7.4 UPDATE CART ITEM QUANTITY
     */
    public ShoppingCartItem updateItemQuantity(ShoppingCartItem item, Integer quantity) {
        item.setQuantity(quantity);
        return this.repository.save(item);
    }

    /**
     * 7.5 EMPTY SHOPPING CART
     */
    public void emptyShoppingCart(UUID cartId) {
        this.repository.deleteByCartId(cartId);
    }


}
