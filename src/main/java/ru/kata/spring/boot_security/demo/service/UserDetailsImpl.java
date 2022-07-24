package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

@Service
public class UserDetailsImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    private UserService userService;

    @Autowired
    public UserDetailsImpl(UserService userService) {
        this.userService = userService;
    }

    public UserDetailsImpl() {}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByName(username);
    }
}
