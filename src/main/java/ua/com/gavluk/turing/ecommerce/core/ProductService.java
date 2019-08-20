package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.repo.*;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.utils.PageableList;
import ua.com.gavluk.turing.utils.PagingSettings;
import ua.com.gavluk.turing.utils.SortOrder;
import ua.com.gavluk.turing.utils.spring.SpringDataUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final AttributeRepository attributeRepository;
    private final DepartmentRepository departmentRepository;
    private final CategoryRepository categoryRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final ProductReviewRepository productReviewRepository;

    @Autowired
    public ProductService(
            ProductRepository repository,
            AttributeRepository attributeRepository,
            DepartmentRepository departmentRepository,
            CategoryRepository categoryRepository,
            AttributeValueRepository attributeValueRepository, ProductReviewRepository productReviewRepository)
    {
        this.repository = repository;
        this.attributeRepository = attributeRepository;
        this.departmentRepository = departmentRepository;
        this.categoryRepository = categoryRepository;
        this.attributeValueRepository = attributeValueRepository;
        this.productReviewRepository = productReviewRepository;
    }

    /**
     * 4.1 GET ALL PRODUCTS
     */
    public PageableList<Product> findAll(PagingSettings paging) {
        Page<Product> all = this.repository.findAll(SpringDataUtils.buildPageable(paging));
        return SpringDataUtils.buildPageableList(all, paging);
    }

    /**
     * 4.3 GET A SINGLE PRODUCT
     */
    public Optional<Product> findById(Long id) {
        return this.repository.findById(id);
    }

    /**
     * 3.1 GET ALL ATTRIBUTES
     */
    public List<Attribute> findAllAttributes() {
        return this.attributeRepository.findAll();
    }

    /**
     * 3.2 GET SINGLE ATTRIBUTES
     */
    public Optional<Attribute> findAttributeById(Long id) {
        return this.attributeRepository.findById(id);
    }

    /**
     * 2.4 GET ALL CATEGORIES IN A DEPARTMENT
     */
    public PageableList<Category> findAllCategoriesInDepartment(Department department) {
        Page<Category> categories = this.categoryRepository.findByDepartmentId(department.getId(), Pageable.unpaged());
        PagingSettings allPaging = new PagingSettings(1, 1000, "category_id", SortOrder.ASC);
        return SpringDataUtils.buildPageableList(categories, allPaging);
    }

    /**
     * 4.2 SEARCH PRODUCTS
     */
    public PageableList<Product> searchByQuery(String queryString, boolean allWords, PagingSettings pagingSettings) {
        String[] words = queryString.split("\\s+");
        Page<Product> products = this.repository.customSearch(words, allWords, SpringDataUtils.buildPageable(pagingSettings));
        return SpringDataUtils.buildPageableList(products, pagingSettings);
    }

    /**
     * 2.1 GET ALL CATEGORIES
     */
    public PageableList<Category> findAllCategories() {
        PagingSettings allPaging = new PagingSettings(1, 1000, "category_id", SortOrder.ASC);
        Page<Category> categories = this.categoryRepository.findAll(Pageable.unpaged());
        return SpringDataUtils.buildPageableList(categories, allPaging);
    }

    /**
     * 2.2 GET A SINGLE CATEGORY
     */
    public Optional<Category> findCategoryById(Long id) {
        return this.categoryRepository.findById(id);
    }

    /**
     * 3.3 GET ALL ATTRIBUTE VALUES IN AN ATTRIBUTE
     */
    public List<AttributeValue> findAttributeValuesFor(Attribute attribute) {
        return this.attributeValueRepository.findByAttributeId(attribute.getId());
    }

    public ProductReview postReview(Customer customer, ProductReviewForm form) throws NotFoundException {

        Product product = this.findById(form.getProductId()).orElseThrow(
                ()-> new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND)
        );

        ProductReview reviewObj = new ProductReview(
                customer.getId(),
                product.getId(),
                form.getReview(),
                form.getRating(),
                Instant.now()
        );

        return this.productReviewRepository.save(reviewObj);
    }

    public PageableList<ProductReview> findProductReviews(Product product, PagingSettings pagingSettings) {
        Page<ProductReview> reviews = this.productReviewRepository.findByProductId(product.getId(), SpringDataUtils.buildPageable(pagingSettings));
        return SpringDataUtils.buildPageableList(reviews, pagingSettings);
    }
}
