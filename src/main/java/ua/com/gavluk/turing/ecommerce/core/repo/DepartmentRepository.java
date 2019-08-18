package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends Repository<Department, Long> {

    List<Department> findAll();

    Optional<Department> findById(Long id);
}
