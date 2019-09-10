package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.Product;
import ua.com.gavluk.turing.ecommerce.core.ProductReview;

import java.util.Optional;

public interface ProductReviewRepository extends Repository<ProductReview, Long> {

    <S extends ProductReview> S save(S entity);

    Optional<ProductReview> findById(Long id);

    Page<ProductReview> findByProductId(Long productId, Pageable pageable);

}
