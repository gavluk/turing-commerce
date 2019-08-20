package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.gavluk.turing.ecommerce.core.Attribute;
import ua.com.gavluk.turing.ecommerce.core.AttributeValue;
import ua.com.gavluk.turing.ecommerce.core.ProductService;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/attributes")
public class AttributesController {

    private ProductService service;

    @Autowired
    public AttributesController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Attribute> findAllAttributes() {
        return this.service.findAllAttributes();
    }

    @GetMapping("/{id}")
    public Attribute findAttributeById(@PathVariable Long id) throws NotFoundException {
        return this.service.findAttributeById(id).orElseThrow(
                ()-> new NotFoundException(NotFoundException.ATTRIBUTE_NOT_FOUND)
        );
    }

    @GetMapping("/values/{attributeId}")
    public List<AttributeValue> findAttributeValues(@PathVariable Long attributeId) throws NotFoundException {

        Attribute attribute = this.service.findAttributeById(attributeId).orElseThrow(
                ()-> new NotFoundException(NotFoundException.ATTRIBUTE_NOT_FOUND)
        );

        return this.service.findAttributeValuesFor(attribute);
    }

}
