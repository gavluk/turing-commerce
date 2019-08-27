package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.gavluk.turing.ecommerce.core.Product;
import ua.com.gavluk.turing.ecommerce.core.ProductService;
import ua.com.gavluk.turing.ecommerce.core.ShoppingCartItem;
import ua.com.gavluk.turing.ecommerce.core.ShoppingCartService;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

    private final ShoppingCartService service;
    private final ProductService productService;

    @Autowired
    public ShoppingCartController(ShoppingCartService service, ProductService productService) {
        this.service = service;
        this.productService = productService;
    }

    @GetMapping("/generateUniqueId")
    public String generateShoppingCartUniqueId() {
        UUID id = this.service.generateShoppingCartUniqueId();
        return id.toString();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartItem addProductToShoppingCart(AddProductToCartDto addingForm) throws NotFoundException {

        Product product = this.productService.findById(addingForm.getProductId()).orElseThrow(
                ()-> new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND)
        );

        return this.service.addProductToShoppingCart(addingForm.getCartId(), product, addingForm.getAttributes(), addingForm.getQuantity());
    }

    @GetMapping("/{cartId}")
    public List<ShoppingCartItem> fetchShoppingCartItems(@PathVariable UUID cartId) throws ValidationException {

        return this.service.fetchItemsOf(cartId);
    }

    @PutMapping("/update/{itemId}")
    public ShoppingCartItem updateShoppingCartItemQuantity(
            @PathVariable @Positive @NotNull Long itemId,
            @Valid UpdateShoppingCartItemQuantityDto updateDto
    ) throws NotFoundException
    {

        ShoppingCartItem item = this.service.findSjoppingCartItemById(itemId).orElseThrow(
                ()-> new NotFoundException(NotFoundException.CART_ITEM_NOT_FOUND)
        );

        return this.service.updateItemQuantity(item, updateDto.getQuantity());
    }

    @DeleteMapping("/shoppingcart/empty/{cartId}")
    public List<ShoppingCartItem> emptyShoppingCart(@PathVariable UUID cartId) {
        this.service.emptyShoppingCart(cartId);
        return this.service.fetchItemsOf(cartId);
    }

    @DeleteMapping("/removeProduct/{itemId}")
    public MessageDto deleteItemFromShoppingCart(@PathVariable @Positive @NotNull Long itemId) throws NotFoundException {

        ShoppingCartItem item = this.service.findSjoppingCartItemById(itemId).orElseThrow(
                () -> new NotFoundException(NotFoundException.CART_ITEM_NOT_FOUND)
        );

        Optional<Product> productOptional = this.productService.findById(item.getProductId());


        return productOptional
                .map(product -> new MessageDto(String.format("You have deleted product '%s' from your cart", product.getName())))
                .orElseGet(() -> new MessageDto("Item deleted from shopping cart"));
    }

/*
    private UUID checkCartIdIsValidUUID(String cartId) {
        try {
            return UUID.fromString(cartId);
        }
        catch (IllegalArgumentException e) {
            throw new ValidationException(ValidationException.BAD_PARAMETER, "cart_id");
        }
    }
*/
}
