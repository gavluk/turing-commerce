package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.gavluk.turing.ecommerce.core.Attribute;
import ua.com.gavluk.turing.ecommerce.core.AttributeValue;
import ua.com.gavluk.turing.ecommerce.core.Product;
import ua.com.gavluk.turing.ecommerce.core.ProductService;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attributes")
public class AttributesController {

    private final ProductService service;
    private final ProductService productService;

    @Autowired
    public AttributesController(ProductService service, ProductService productService) {
        this.service = service;
        this.productService = productService;
    }

    @GetMapping
    public List<Attribute> findAllAttributes() {
        return this.service.findAllAttributes();
    }

    @GetMapping("/{id}")
    public Attribute findAttributeById(@PathVariable @Positive Long id) throws NotFoundException {
        return this.service.findAttributeById(id).orElseThrow(
                ()-> new NotFoundException(NotFoundException.ATTRIBUTE_NOT_FOUND)
        );
    }

    @GetMapping("/values/{attributeId}")
    public List<AttributeValue> findAttributeValues(@PathVariable @Positive Long attributeId) throws NotFoundException {

        Attribute attribute = this.service.findAttributeById(attributeId).orElseThrow(
                ()-> new NotFoundException(NotFoundException.ATTRIBUTE_NOT_FOUND)
        );

        return this.service.findAttributeValuesFor(attribute);
    }

    @GetMapping("/inProduct/{productId}")
    public List<AttributeValieInProductDTO> findForProduct(@PathVariable @Positive Long productId) throws NotFoundException {
        Product product = this.productService.findById(productId).orElseThrow(
                () -> new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND)
        );
        return product.getAttributeValues().stream().map(AttributeValieInProductDTO::new).collect(Collectors.toList());
    }

}
