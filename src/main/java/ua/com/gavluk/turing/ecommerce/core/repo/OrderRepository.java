package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.Order;
import ua.com.gavluk.turing.ecommerce.core.ProductReview;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Repository<Order, Long> {

    Optional<Order> findById(Long id);

    List<Order> findByCustomerId(Long customerId, Sort sort);

    <S extends Order> S save(S entity);
}
