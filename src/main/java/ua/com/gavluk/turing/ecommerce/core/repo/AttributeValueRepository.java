package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.data.repository.Repository;
import ua.com.gavluk.turing.ecommerce.core.AttributeValue;

import java.util.List;

public interface AttributeValueRepository extends Repository<AttributeValue, Long> {

    List<AttributeValue> findByAttributeId(Long attributeId);
}
