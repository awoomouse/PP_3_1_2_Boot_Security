package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.MyPasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MyPasswordEncoder myPasswordEncoder;
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MyPasswordEncoder myPasswordEncoder, RoleRepository roleRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.myPasswordEncoder = myPasswordEncoder;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    @Transactional
    public User addUser(User user) {
        User findUser = userRepository.findByUsername(user.getUsername());
        if (findUser != null) {
            return findUser;
        }
        user.setPassword(myPasswordEncoder.getPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void updateUserRole(User user, String[] roles) {
        user.setRoleSet(Arrays.stream(roles).map(roleService::getRoleByRoleName).collect(Collectors.toList()));
        userRepository.save(user);
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
    public User editUser(User user, long id) {
        User editUser = this.getUser(id);
        editUser.setFirstName(user.getFirstName());
        editUser.setLastName(user.getLastName());
        editUser.setAge(user.getAge());
        editUser.setEmail(user.getEmail());
        editUser.setUsername(user.getUsername());
        editUser.setPassword(myPasswordEncoder.getPasswordEncoder().encode(user.getPassword()));
        return editUser;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}