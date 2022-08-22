package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class SpringBootSecurityDemoApplicationTests {

	private final RoleRepository roleRepository;
	private final UserService userService;

	@Autowired
	public SpringBootSecurityDemoApplicationTests(RoleRepository roleRepository, UserService userService) {
		this.roleRepository = roleRepository;
		this.userService = userService;
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void createAdmin() {
		Role roleAdmin = new Role( "ROLE_ADMIN");
		Role roleUser = new Role( "ROLE_USER");
		List<Role> roles = new ArrayList<>();
		roles.add(roleAdmin);
		roles.add(roleUser);
		this.roleRepository.save(roleAdmin);
		this.roleRepository.save(roleUser);
		User user = this.userService.addUser(new User("admin", "admin", "testname",
				"testlastname", (byte) 15, "email@email.com", roles));
	}
}
