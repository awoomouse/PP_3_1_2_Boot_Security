package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class AdminController {
    private final UserService userDao;

    @Autowired
    public AdminController(UserService userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/admin/users")
    public String getUsers(ModelMap model) {
        model.addAttribute("usersList", this.userDao.getAllUsers());
        return "users";
    }

    @GetMapping("/admin/user/add")
    public String addUser(@ModelAttribute("user") User user) {
        return "addUser";
    }

    @PostMapping("/admin/users")
    public String createUser(@ModelAttribute("user") User user) {
        this.userDao.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserForAdmin(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", this.userDao.getUser(id));
        return "show";
    }

    @GetMapping("/admin/user/{id}/update")
    public String getUpdateUserForm(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", this.userDao.getUser(id));
        return "updateUser";
    }

    @PatchMapping("/admin/user/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        this.userDao.editUser(user, id);
        return "show";
    }

    @DeleteMapping("/admin/user/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        this.userDao.deleteUser(id);
        return "redirect:/admin/users";
    }
}