package com.project.garden.app.gate;

import com.project.garden.app.gate.model.Gate;
import com.project.garden.core.Base.BaseSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class GateSpecification extends BaseSpecification<Gate> {

    private final String name;
    private final String phoneNumber;
    private final Long userId;

    public GateSpecification(Long id, Date startDate, Date endDate, List<Long> ids, String name, String phoneNumber, Long userId) {
        super(id, startDate, endDate, ids);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    @Override
    public void prePredicate(Root<Gate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (name != null)
            addPredicate(builder.like(root.get("name"), "%" + name + "%"));
        if (phoneNumber != null)
            addPredicate(builder.like(root.get("phone").get("number"), "%" + phoneNumber + "%"));
        if (userId != null)
            addPredicate(builder.equal(root.get("user").get("id"), userId));
    }
}
