package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.Tax;

import java.util.List;
import java.util.Optional;

public interface TaxRepository extends Repository<Tax,Long> {

    List<Tax> findAll(Sort sort);

    Optional<Tax> findById(Long id);
}
