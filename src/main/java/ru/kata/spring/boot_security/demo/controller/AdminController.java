package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class AdminController {
    private final UserService userDao;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getUsers(Principal principal, Model model) {
        model.addAttribute("usersList", this.userDao.getAllUsers());

        return "users";
    }

    @GetMapping("/admin/user/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roleList", this.roleService.getAllRoles());
        return "addUser";
    }

    @PostMapping("/admin/users/add")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "rolesList") String[] roles) {
        this.userDao.addUser(user);
        this.userDao.updateUserRole(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserForAdmin(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", this.userDao.getUser(id));
        return "show";
    }

    @GetMapping("/admin/user/{id}/update")
    public String getUpdateUserForm(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", this.userDao.getUser(id));
        model.addAttribute("roleList", this.roleService.getAllRoles());
        return "updateUser";
    }

    @PatchMapping("/admin/user/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "rolesList") String[] roles, @PathVariable("id") long id) {
        User editUser = this.userDao.editUser(user, id);
        this.userDao.updateUserRole(editUser, roles);
        return "show";
    }

    @DeleteMapping("/admin/user/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        this.userDao.deleteUser(id);
        return "redirect:/admin/users";
    }
}