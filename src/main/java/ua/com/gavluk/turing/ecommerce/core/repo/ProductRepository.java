package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.Category;
import ua.com.gavluk.turing.ecommerce.core.Product;

import java.util.Optional;

public interface ProductRepository extends Repository<Product, Long>, ProductRepositoryCustom {

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    Page<Product> findByCategoriesIn(Category[] categories, Pageable buildPageable);
}
