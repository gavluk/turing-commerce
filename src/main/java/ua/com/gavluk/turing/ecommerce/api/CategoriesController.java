package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.gavluk.turing.ecommerce.core.Category;
import ua.com.gavluk.turing.ecommerce.core.ProductService;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.utils.PageableList;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private ProductService service;

    @Autowired
    public CategoriesController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public PageableList<Category> findAllCategories() {
        return this.service.findAllCategories();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) throws NotFoundException {
        return this.service.findCategoryById(id).orElseThrow(
                ()-> new NotFoundException(NotFoundException.CATEGORY_NOT_FOUND)
        );
    }
}
