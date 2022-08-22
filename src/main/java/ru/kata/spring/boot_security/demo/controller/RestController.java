package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserService userDao;


    @Autowired
    public RestController(UserService userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/admin")
    public List<User> getUsers() {
        List<User> userList = userDao.getAllUsers();
        return userList;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") long id) {
        User user = userDao.getUser(id);
        return user;
    }

    @PostMapping("/admin")
    public User createUser(@RequestBody User user) {
        userDao.addUser(user);
        return user;
    }

    @PutMapping("/admin")
    public User updateUser(@RequestBody User user) {
        return userDao.editUser(user);
    }

    @DeleteMapping("/admin/{id}")
    public HttpEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        userDao.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
