package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.Shipping;

import java.util.List;
import java.util.Optional;

public interface ShippingRepository extends Repository<Shipping, Long> {

    List<Shipping> findByRegionId(Long regionId);

    Optional<Shipping> findById(Long id);
}
