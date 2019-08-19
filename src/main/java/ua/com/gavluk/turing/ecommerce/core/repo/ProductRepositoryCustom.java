package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.gavluk.turing.ecommerce.core.Product;

public interface ProductRepositoryCustom {

    Page<Product> customSearch(String[] words, boolean allWords, Pageable pageable);

}
