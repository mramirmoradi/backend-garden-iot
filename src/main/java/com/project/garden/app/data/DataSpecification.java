package com.project.garden.app.data;

import com.project.garden.app.data.model.Data;
import com.project.garden.core.Base.BaseSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class DataSpecification extends BaseSpecification<Data> {

    private final Double min_hin;
    private final Double max_hin;

    private final Double min_hout;
    private final Double max_hout;

    private final Double min_tin;
    private final Double max_tin;

    private final Double min_tout;
    private final Double max_tout;

    private final Double min_co;
    private final Double max_co;

    private final Double min_iux;
    private final Double max_iux;

    private final Long gateId;
    private final String gateName;
    private final Long userId;
    private final String username;

    public DataSpecification(Long id,
                             Date startDate,
                             Date endDate,
                             List<Long> ids,
                             Double min_hin,
                             Double max_hin,
                             Double min_hout,
                             Double max_hout,
                             Double min_tin,
                             Double max_tin,
                             Double min_tout,
                             Double max_tout,
                             Double min_co,
                             Double max_co,
                             Double min_iux,
                             Double max_iux,
                             Long gateId,
                             String gateName,
                             Long userId, String username) {
        super(id, startDate, endDate, ids);
        this.min_hin = min_hin;
        this.max_hin = max_hin;
        this.min_hout = min_hout;
        this.max_hout = max_hout;
        this.min_tin = min_tin;
        this.max_tin = max_tin;
        this.min_tout = min_tout;
        this.max_tout = max_tout;
        this.min_co = min_co;
        this.max_co = max_co;
        this.min_iux = min_iux;
        this.max_iux = max_iux;
        this.gateId = gateId;
        this.gateName = gateName;
        this.userId = userId;
        this.username = username;
    }

    @Override
    public void prePredicate(Root<Data> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (min_hin != null && max_hin != null)
            addPredicate(builder.between(root.get("hin"), min_hin, max_hin));
        if (min_hout != null && max_hout != null)
            addPredicate(builder.between(root.get("hout"), min_hout, max_hout));
        if (min_tin != null && max_tin != null)
            addPredicate(builder.between(root.get("tin"), min_tin, max_tin));
        if (min_tout != null && max_tout != null)
            addPredicate(builder.between(root.get("tout"), min_tout, max_tout));
        if (min_co != null && max_co != null)
            addPredicate(builder.between(root.get("co"), min_co, max_co));
        if (min_iux != null && max_iux != null)
            addPredicate(builder.between(root.get("iux"), min_iux, max_iux));
        if (gateId != null)
            addPredicate(builder.equal(root.get("gate").get("id"), gateId));
        if (gateName != null)
            addPredicate(builder.equal(root.get("gate").get("name"), gateName));
        if (userId != null)
            addPredicate(builder.equal(root.get("gate").get("user").get("id"), userId));
        if (username != null)
            addPredicate(builder.equal(root.get("gate").get("user").get("username"), username));
    }
}
