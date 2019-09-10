package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.gavluk.turing.ecommerce.core.Department;
import ua.com.gavluk.turing.ecommerce.core.ProductService;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmensController {

    private final ProductService service;

    @Autowired
    public DepartmensController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Department> findAllDepartments() {
        return this.service.findAllDepartmens();
    }

    @GetMapping("/{id}")
    public Department findDepartmentById(
            @PathVariable @Positive(message = ValidationException.VALIDATION_CONSTRAINT_MESSAGE_PREFIX + ":DEP_01:The ID is not a number") Long id
    ) throws NotFoundException {
        return this.service.findDepartmentById(id).orElseThrow(
                ()-> new NotFoundException(NotFoundException.DEPARTMENT_NOT_FOUND)
        );
    }


}
