package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    User getUser(long id);

    public User getUserByName(String username);

    void deleteUser(long id);

    void editUser(User user, long id);

    List<User> getAllUsers();
}
