package com.project.garden.security.user.service;

import com.project.garden.app.phone.model.Phone;
import com.project.garden.app.phone.service.PhoneService;
import com.project.garden.core.Base.BaseServiceImp;
import com.project.garden.core.exception.Error;
import com.project.garden.core.exception.ErrorStatus;
import com.project.garden.security.user.model.Role;
import com.project.garden.security.user.model.User;
import com.project.garden.security.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation extends BaseServiceImp<User> implements UserService {

    private final UserRepository repository;
    private final PhoneService phoneService;
    @Autowired
    public UserServiceImplementation(UserRepository repository, PhoneService phoneService) {
        super(repository);
        this.repository = repository;
        this.phoneService = phoneService;
    }

    @Override
    public boolean isExist(long id) {
        return repository.findById(id).isPresent();
    }

    @Override
    public boolean isExist(String username, String email) {
        return repository.findByUsernameOrEmail(username, email).isPresent();
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = repository.findByUsername(username);
        if (userOptional.isEmpty())
            throw new Error(ErrorStatus.NOTFOUND, User.class.getSimpleName(), "user not fount", null);
        return userOptional.get();
    }

    @Override
    public User findByPhone(Phone phone) {
        Optional<User> optionalUser = repository.findByPhone(phone);
        if (optionalUser.isPresent())
            return optionalUser.get();
        else
            throw new Error(ErrorStatus.NOTFOUND, User.class.getSimpleName(), "user not found", null);
    }

    @Override
    public void populate() {
        if (repository.count() > 0)
            return;
        this.create(
                User.builder()
                .username("admin")
                .email("engiamirmoradi@gmail.com")
                .role(Role.ADMIN)
                .phone(Phone.builder()
                        .number("09031388340")
                        .build())
                .password(null)
                .build());
    }
}
