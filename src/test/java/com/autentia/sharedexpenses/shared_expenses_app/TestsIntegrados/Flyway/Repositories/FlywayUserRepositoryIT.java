package com.autentia.sharedexpenses.shared_expenses_app.TestsIntegrados.Flyway.Repositories;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.MySqlUserRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlywayUserRepositoryIT {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @Autowired
    private MySqlUserRepository userRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeAll
    static void beforeAll(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Order(1)
    public void shouldFindAllUsers() {
        List<User> users = userRepository.findAll();
        assertEquals(3, users.size());
    }

    @Test
    @Order(2)
    public void shouldSaveUser() {

        User user = new User(4L, "Mochi");
        userRepository.save(user);

        assertTrue(userRepository.existsById(user.getId()));
    }

    @Test
    @Order(3)
    public void shouldFindUserById() {

        Optional<User> user = userRepository.findById(1L);
        assertTrue(user.isPresent());
    }

    @Test
    @Order(4)
    public void shouldUpdateUser() {

        User user = new User(1L, "Maria updated");

        userRepository.updateUser(user, user.getId());

        Optional<User> updatedUser = userRepository.findById(user.getId());

        assertEquals("Maria updated", updatedUser.get().getName());

    }

    @Test
    @Order(5)
    public void shouldDeleteUser() {

        userRepository.deleteById(4L);
        assertFalse(userRepository.existsById(4L));

    }

    @Test
    @Order(6)
    public void shouldVerifyIfUserExists() {
        assertTrue(userRepository.existsById(1L));
    }

}
