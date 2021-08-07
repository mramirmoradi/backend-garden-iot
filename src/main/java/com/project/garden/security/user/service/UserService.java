package com.project.garden.security.user.service;

import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.Base.BaseService;
import com.project.garden.security.user.model.User;

public interface UserService extends BaseService<User> {

    boolean isExist(long id);

    boolean isExist(String username, String email);

    User findByUsername(String username);

    User findByPhone(Phone phone);

    void populate();
}
