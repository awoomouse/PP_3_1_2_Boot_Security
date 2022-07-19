package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class AdminUsersController {
    private final UserService userDao;

    @Autowired
    public AdminUsersController(UserService userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/admin/users")
    public String showUsers(ModelMap model) {
        model.addAttribute("usersList", this.userDao.getAllUsers());
        return "users";
    }

    @GetMapping("/admin/users/add")
    public String addUser(@ModelAttribute("user") User user) {
        return "addUser";
    }

    @PostMapping("/admin/users")
    public String createUser(@ModelAttribute("user") User user) {
        this.userDao.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/{id}")
    public String showUserForAdmin(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", this.userDao.getUser(id));
        return "show";
    }

    @GetMapping("/admin/users/{id}/update")
    public String getUpdateUserForm(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", this.userDao.getUser(id));
        return "updateUser";
    }

    @PatchMapping("/admin/users/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        this.userDao.editUser(user, id);
        return "show";
    }

    @DeleteMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        this.userDao.deleteUser(id);
        return "redirect:/admin/users";
    }
}