package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.gavluk.turing.ecommerce.core.Tax;
import ua.com.gavluk.turing.ecommerce.core.TaxService;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/tax")
public class TaxController {

    private TaxService service;

    @GetMapping
    public List<Tax> findAll() {
        return this.service.findAllTaxes();
    }

    @GetMapping("/{id}")
    public Tax findById(@Positive @NotNull Long id) throws NotFoundException {
        return this.service.findById(id).orElseThrow(
                ()-> new NotFoundException(NotFoundException.TAX_NOT_FOUND)
        );
    }
}
