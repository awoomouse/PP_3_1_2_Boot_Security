package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class CreateAdmin {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public CreateAdmin(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/addAdmin")
    public String createAdminAndRoles() {
        Role roleAdmin = new Role(1L, "ROLE_ADMIN");
        Role roleUser = new Role(2L, "ROLE_USER");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
        User user = userService.addUser(new User("admin", "admin", "testname",
                "testlastname", (byte) 15, "email@email.com"));
        userService.updateUserRole(user, roleAdmin);
        return "redirect:/login";
    }
}
