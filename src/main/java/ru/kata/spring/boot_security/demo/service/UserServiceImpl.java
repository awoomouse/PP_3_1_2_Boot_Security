package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.PasswordEncoderTest;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.List;

@Repository
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderTest passwordEncoderTest;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoderTest passwordEncoderTest) {
        this.userRepository = userRepository;
        this.passwordEncoderTest = passwordEncoderTest;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User addUser(User user) {
        User findUser = userRepository.findByUsername(user.getUsername());
        if (findUser != null) {
            return findUser;
        }
        user.addRole(new Role(2L, "ROLE_USER"));
        user.setPassword(passwordEncoderTest.getPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void updateRole(User user, Role role) {
        user.addRole(role);
    }

    public User getUser(long id) {
        return userRepository.getById(id);
    }

    @SuppressWarnings("unchecked")
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void editUser(User user, long id) {
        User editUser = this.getUser(id);
        editUser.setFirstName(user.getFirstName());
        editUser.setLastName(user.getLastName());
        editUser.setAge(user.getAge());
        editUser.setEmail(user.getEmail());
        editUser.setUsername(user.getUsername());
    }

    @Transactional
    public void editPassword(User user, long id) {
        User editUser = this.getUser(id);
        editUser.setPassword(passwordEncoderTest.getPasswordEncoder().encode(user.getPassword()));
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}