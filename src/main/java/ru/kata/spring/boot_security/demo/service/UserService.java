package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    User getUser(long id);

    public User getUserByName(String username);

    void deleteUser(long id);

    void editUser(User user, long id);

    List<User> getAllUsers();
}
