package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.core.repo.ShippingRegionRepository;
import ua.com.gavluk.turing.ecommerce.core.repo.ShippingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {
    private final ShippingRegionRepository regionRepository;
    private final ShippingRepository repository;

    @Autowired
    public ShippingService(ShippingRegionRepository regionRepository, ShippingRepository repository) {
        this.regionRepository = regionRepository;
        this.repository = repository;
    }

    public List<ShippingRegion> findAllRegions() {
        return this.regionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<ShippingRegion> findRegionById(Long regionId) {
        return this.regionRepository.findById(regionId);
    }

    public List<Shipping> findAllInRegion(ShippingRegion region) {
        return this.repository.findByRegionId(region.getId());
    }

    public Optional<Shipping> findById(Long id) {
        return this.repository.findById(id);
    }
}
