package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.gavluk.turing.ecommerce.core.Product;
import ua.com.gavluk.turing.ecommerce.core.ProductReview;
import ua.com.gavluk.turing.ecommerce.core.ProductReviewForm;
import ua.com.gavluk.turing.ecommerce.core.ProductService;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.utils.PageableList;
import ua.com.gavluk.turing.utils.PagingSettings;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {


    private final ProductService service;

    @Autowired
    public ProductsController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public PageableList<Product> findAll(
            @Valid PagingDTO pagingDTO,
            @RequestParam(name = "description_length", defaultValue = "200") Integer descriptionLength
    )
    {
        // TODO: ask Product owner why there is no sorting requirements
        PageableList<Product> all = this.service.findAll(new PagingSettings(pagingDTO.getPage(), pagingDTO.getLimit()));

        // truncating description is not part of service but client-specific, so do it right in controller
        all.getRows().stream().forEach((x) -> x.truncateDescriptionTo(descriptionLength));

        return all;
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) throws NotFoundException {
        return this.service.findById(id).orElseThrow(
                () -> new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND)
        );
    }

    @GetMapping("/{productId}/reviews")
    public PageableList<ProductReview> findProductReviews(@PathVariable Long productId, @Valid PagingDTO pagingDTO) throws NotFoundException {
        Product product = this.service.findById(productId).orElseThrow(
                () -> new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND)
        );
        return this.service.findProductReviews(product, new PagingSettings(pagingDTO.getPage(), pagingDTO.getLimit()));
    }

    @PostMapping("/{productId}/reviews")
    public ProductReview postNewReview(@PathVariable Long productId,
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
