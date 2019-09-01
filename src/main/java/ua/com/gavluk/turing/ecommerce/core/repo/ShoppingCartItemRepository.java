package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.ShoppingCartItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartItemRepository extends Repository<ShoppingCartItem, Long> {

    <S extends ShoppingCartItem> S saveAndFlush(S entity);

    Optional<ShoppingCartItem> findById(Long id);

    List<ShoppingCartItem> findByCartId(UUID cartId, Sort sort);

    void deleteByCartId(UUID cartId);

    List<ShoppingCartItem>  findByCartIdAndProductId(UUID cartId, Long id);

    void deleteById(Long id);
}
