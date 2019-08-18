package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.repo.AttributeRepository;
import ua.com.gavluk.turing.ecommerce.core.repo.CategoryRepository;
import ua.com.gavluk.turing.ecommerce.core.repo.DepartmentRepository;
import ua.com.gavluk.turing.ecommerce.core.repo.ProductRepository;
import ua.com.gavluk.turing.utils.PageableList;
import ua.com.gavluk.turing.utils.PagingSettings;
import ua.com.gavluk.turing.utils.SortOrder;
import ua.com.gavluk.turing.utils.spring.SpringDataUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private AttributeRepository attributeRepository;
    private final DepartmentRepository departmentRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(
            ProductRepository repository,
            AttributeRepository attributeRepository,
            DepartmentRepository departmentRepository,
            CategoryRepository categoryRepository
    ) {
        this.repository = repository;
        this.attributeRepository = attributeRepository;
        this.departmentRepository = departmentRepository;
        this.categoryRepository = categoryRepository;
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

    public List<Attribute> findAllAttributes() {
        return this.attributeRepository.findAll();
    }

    public Optional<Attribute> findAttributeById(Long id) {
        return this.attributeRepository.findById(id);
    }

    /**
     * 2.4 GET ALL CATEGORIES IN A DEPARTMENT
     */
    public PageableList<Category> findAllCategoriesInDepartment(Department department) {
        Page<Category> categories = this.categoryRepository.findByDepartmentId(department.getId(), Pageable.unpaged());
        PagingSettings allById = new PagingSettings(1, 1000, "category_id", SortOrder.ASC);
        return SpringDataUtils.buildPageableList(categories, allById);
    }
}
