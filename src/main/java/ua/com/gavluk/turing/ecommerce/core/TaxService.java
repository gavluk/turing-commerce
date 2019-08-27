package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ua.com.gavluk.turing.ecommerce.core.repo.TaxRepository;

import java.util.List;
import java.util.Optional;

public class TaxService {
    private final TaxRepository repository;

    @Autowired
    public TaxService(TaxRepository repository) {
        this.repository = repository;
    }

    public List<Tax> findAllTaxes() {
        return this.repository.findAll(Sort.by(new Sort.Order(Sort.Direction.ASC, "id")));
    }

    public Optional<Tax> findById(Long id) {
        return this.repository.findById(id);
    }
}
