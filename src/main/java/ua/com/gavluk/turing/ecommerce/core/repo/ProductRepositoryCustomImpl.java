package ua.com.gavluk.turing.ecommerce.core.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ua.com.gavluk.turing.ecommerce.core.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    EntityManager em;

    @Autowired
    public ProductRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Page<Product> customSearch(String[] words, boolean allWords, Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> from = cq.from(Product.class);
        CriteriaQuery<Product> select = cq.select(from);

        List<Predicate> predicates = new ArrayList<>();

        Predicate finalPredicate;

        for (int i = 0; i < words.length; i++) {
            Predicate nameHasWord = cb.like(cb.lower(from.get("name")), "%" + words[i].toLowerCase() + "%");
            Predicate descHasWord = cb.like(cb.lower(from.get("description")), "%" + words[i].toLowerCase() + "%");
            predicates.add( cb.or(nameHasWord, descHasWord) );
        }

        if (allWords)
            finalPredicate = cb.and(predicates.toArray(new Predicate[0]));
        else
            finalPredicate = cb.or(predicates.toArray(new Predicate[0]));

        cq.where(finalPredicate);

        TypedQuery<Product> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult((int)pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // TODO: add count() calculation and calling for totalCount
        Page<Product> products = new PageImpl<>(typedQuery.getResultList());
        return products;
    }
}
