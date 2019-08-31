package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.OrderItem;

import java.util.List;

public interface OrderItemRepository extends Repository<OrderItem, Long> {

    public List<OrderItem> findByOrderId(Long orderId);

    <S extends OrderItem> S save(S entity);
}
