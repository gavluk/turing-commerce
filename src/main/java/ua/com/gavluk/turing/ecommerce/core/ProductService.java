package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ua.com.gavluk.turing.ecommerce.repo.ProductRepository;
import ua.com.gavluk.turing.utils.PageableList;
import ua.com.gavluk.turing.utils.PagingSettings;
import ua.com.gavluk.turing.utils.spring.SpringDataUtils;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public PageableList<Product> findAll(PagingSettings paging) {
        Page<Product> all = this.repository.findAll(SpringDataUtils.buildPageable(paging));
        return SpringDataUtils.buildPageableList(all, paging);
    }

}
