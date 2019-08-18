package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.Attribute;

import java.util.List;
import java.util.Optional;

public interface AttributeRepository extends Repository<Attribute, Long> {

    List<Attribute> findAll();

    Optional<Attribute> findById(Long id);

}
