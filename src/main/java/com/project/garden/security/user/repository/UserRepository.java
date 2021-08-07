package com.project.garden.security.user.repository;

import com.project.garden.app.phone.model.Phone;
import com.project.garden.core.Base.BaseRepository;
import com.project.garden.security.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByPhone(Phone phone);

    Optional<User> findByEmail(String email);

}
