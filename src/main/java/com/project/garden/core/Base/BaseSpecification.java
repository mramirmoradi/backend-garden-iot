package com.project.garden.core.Base;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseSpecification<T extends BaseEntity> implements Specification<T> {


    private static final long serialVersionUID = 1L;
    protected Long id;
    protected Date startDate;
    protected Date endDate;
    private final List<Long> ids;

    private final List<Predicate> predicates;
    private Predicate predicateFinal;

    public BaseSpecification(Long id) {
        this.id = id;
        ids = new ArrayList<>();
        predicates = new ArrayList<>();
    }

    public BaseSpecification(Long id, Date startDate, Date endDate, List<Long> ids) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ids = ids;
        predicates = new ArrayList<>();
    }

    public void addPredicate(Predicate predicate) {
        this.predicates.add(predicate);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        prePredicate(root, query, builder);
        doPredicate(root, query, builder);
        postPredicate(root, query, builder);
        return predicateFinal;
    }

    public void doPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (id != null)
            this.addPredicate(builder.equal(root.get("id"), id));
        if (startDate != null && endDate != null)
            addPredicate(builder.between(root.get("createdDate"), startDate, endDate));
        if (ids != null && !ids.isEmpty()) {
            Predicate tempPredicate = builder.equal(root.get("id"), ids.get(0));
            for (Long id : ids)
                tempPredicate = builder.or(tempPredicate, builder.equal(root.get("id"), id));
            addPredicate(tempPredicate);
        }
        query.distinct(true);
        List<Order> orders = new ArrayList<>();
        orders.add(builder.desc(root.get("sequence")));
        orders.add(builder.desc(root.get("createdDate")));
        query.orderBy(orders);
    }

    public void postPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (predicates.isEmpty())
            predicateFinal = builder.isNotNull(root.get("id"));
        else
            predicateFinal = predicates.get(0);
        for (Predicate pt : predicates) {
            predicateFinal = builder.and(predicateFinal, pt);
        }
    }

    public void prePredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    }


}
