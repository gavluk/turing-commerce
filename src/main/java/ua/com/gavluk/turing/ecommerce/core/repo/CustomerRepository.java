package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.Customer;

import java.util.Optional;

public interface CustomerRepository extends Repository<Customer, Long> {

    <S extends Customer> S save(S entity);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByEmail(String email);
}
