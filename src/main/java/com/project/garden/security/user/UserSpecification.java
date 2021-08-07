package com.project.garden.security.user;

import com.project.garden.core.Base.BaseSpecification;
import com.project.garden.security.user.model.Role;
import com.project.garden.security.user.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class UserSpecification extends BaseSpecification<User> {

    private final String username;
    private final String email;
    private final String phoneNumber;
    private final Role role;

    public UserSpecification(
            Long id,
            Date startDate,
            Date endDate,
            List<Long> ids,
            String username,
            String email,
            String phoneNumber,
            Role role) {

        super(id, startDate, endDate, ids);
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }


    @Override
    public void prePredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (username != null)
            addPredicate(builder.like(root.get("username"), "%" + username + "%"));
        if (email != null)
            addPredicate(builder.like(root.get("email"), "%" + email + "%"));
        if (phoneNumber != null)
            addPredicate(builder.equal(root.get("phone").get("number"), phoneNumber));
        if (role != null)
            addPredicate(builder.equal(root.get("role"), role));
    }
}
