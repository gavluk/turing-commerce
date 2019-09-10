package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.ShippingRegion;

import javax.swing.plaf.synth.Region;
import java.util.List;
import java.util.Optional;

public interface ShippingRegionRepository extends Repository<ShippingRegion, Long> {

    List<ShippingRegion> findAll(Sort sort);

    Optional<ShippingRegion> findById(Long id);
}
