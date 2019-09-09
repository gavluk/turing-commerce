package ua.com.gavluk.turing.ecommerce.api;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.gavluk.turing.ecommerce.core.*;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.utils.PageableList;
import ua.com.gavluk.turing.utils.PagingSettings;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/products")
public class ProductsController {


    private final ProductService service;

    @Autowired
    public ProductsController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    @JsonView(ViewProfile.Basic.class)
    public PageableList<Product> findAll(
            @Valid PagingDTO pagingDTO,
            @RequestParam(name = "description_length", defaultValue = "200") Integer descriptionLength
    )
    {
        // TODO: Q: ask Product owner why there is no sorting requirements
        PageableList<Product> all = this.service.findAll(new PagingSettings(pagingDTO.getPage(), pagingDTO.getLimit()));

        // truncating description is not part of service but client-specific, so do it right in controller
        all.getRows().forEach((x) -> x.truncateDescriptionTo(descriptionLength));

        return all;
    }

    @GetMapping("/inCategory/{categoryId}")
    @JsonView(ViewProfile.Basic.class)
    public PageableList<Product> findAllInCategory(
            @PathVariable @Positive Long categoryId,
            @Valid PagingDTO pagingDTO,
            @RequestParam(name = "description_length", defaultValue = "200") Integer descriptionLength
    ) throws NotFoundException
    {
        Category category = this.service.findCategoryById(categoryId).orElseThrow(
                () -> new NotFoundException(NotFoundException.CATEGORY_NOT_FOUND)
        );

        PageableList<Product> all = this.service.findByCategories(new PagingSettings(pagingDTO.getPage(), pagingDTO.getLimit()), category);

        all.getRows().forEach((x) -> x.truncateDescriptionTo(descriptionLength));

        return all;
    }


    @GetMapping("/inDepartment/{departmentId}")
    @JsonView(ViewProfile.Basic.class)
    public PageableList<Product> findAllInDepartment(
            @PathVariable @Positive Long departmentId,
            @Valid PagingDTO pagingDTO,
            @RequestParam(name = "description_length", defaultValue = "200") Integer descriptionLength
    ) throws NotFoundException
    {
        Department department = this.service.findDepartmentById(departmentId).orElseThrow(
                () -> new NotFoundException(NotFoundException.DEPARTMENT_NOT_FOUND)
        );
        PageableList<Category> categoriesPageableList = this.service.findAllCategoriesInDepartment(department);

        PageableList<Product> all = this.service.findByCategories(
                new PagingSettings(pagingDTO.getPage(), pagingDTO.getLimit()),
                categoriesPageableList.getRows().toArray(new Category[] {})
        );

        all.getRows().forEach((x) -> x.truncateDescriptionTo(descriptionLength));

        return all;
    }

    @GetMapping("/{id}")
    @JsonView(ViewProfile.Full.class)
    public Product findById(@PathVariable @Positive @NotNull Long id) throws NotFoundException {
        return this.service.findById(id).orElseThrow(
                () -> new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND)
        );
    }

    @GetMapping("/{productId}/reviews")
    public PageableList<ProductReview> findProductReviews(
            @PathVariable @Positive @NotNull Long productId,
            @Valid PagingDTO pagingDTO
    ) throws NotFoundException
    {
        Product product = this.service.findById(productId).orElseThrow(
                () -> new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND)
        );
        return this.service.findProductReviews(product, new PagingSettings(pagingDTO.getPage(), pagingDTO.getLimit()));
    }

    @PostMapping("/{productId}/reviews")
    public ProductReview postNewReview(
            @PathVariable @Positive @NotNull Long productId,
            @Valid @RequestBody ProductReviewForm form,
            CustomerAuthentication authentication
    ) throws NotFoundException
    {
        Product product = this.service.findById(productId).orElseThrow(
                () -> new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND)
        );

        return this.service.postReview(authentication.getCustomer(), product, form);
    }

    @GetMapping("/search")
    public PageableList<Product> searchProducts(@Valid PagingDTO pagingDTO, @Valid SearchQueryDTO searchQueryDTO)
    {
        PageableList<Product> all = this.service.searchByQuery(
                searchQueryDTO.getQuery_string(),
                searchQueryDTO.isAllWords(),
                new PagingSettings(pagingDTO.getPage(), pagingDTO.getLimit())
        );

        return all;
    }


}
