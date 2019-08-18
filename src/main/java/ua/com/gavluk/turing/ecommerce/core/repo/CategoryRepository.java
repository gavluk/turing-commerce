package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.Category;

import java.util.Optional;

public interface CategoryRepository extends Repository<Category, Long> {

    Page<Category> findAll(Pageable pageable);

    Optional<Category> findById(Long id);

    Page<Category> findByDepartmentId(Long departmentId, Pageable pageable);

/*
 TODO: to think how to do it: native query OR hibernate tricks OR synthetic repo for product_category
    @Query(value="SELECT c FROM Category c WHERE id = ")
    Optional<Category> findByProductId(Long productId);
*/

}
