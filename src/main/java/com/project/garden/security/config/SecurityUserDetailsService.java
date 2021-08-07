package com.project.garden.security.config;

import com.project.garden.security.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;
    @Autowired
    public SecurityUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = userService.findByUsername(s);
        return new User(user.getId() + "", null, new ArrayList<>());
    }
}
