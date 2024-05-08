package com.autentia.sharedexpenses.shared_expenses_app.TestsIntegrados;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.MySqlUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MySqlUserRepository userRepository;

    @BeforeEach
    public void setup() {
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS User(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(80) NOT NULL)");
        jdbcTemplate.update("DELETE FROM User");
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM User");
    }

    @Test
    public void shouldFindAllUsers() {

        userRepository.save(new User(1L, "Maria"));
        userRepository.save(new User(2L, "Juan"));
        userRepository.save(new User(3L, "Belen"));

        List<User> users = userRepository.findAll();

        assertEquals(3, users.size());

    }

    @Test
    public void shouldSaveUser() {

        User user = new User(1L, "Maria");
        userRepository.save(user);

        assertTrue(userRepository.existsById(user.getId()));

    }

    @Test
    public void shouldFindUserById() {

        User user = new User(1L, "Maria");
        userRepository.save(user);

        Optional<User> createdUser = userRepository.findById(user.getId());
        assertTrue(createdUser.isPresent());

    }

    @Test
    public void shouldUpdateUser() {

        User user = new User(4L, "Nani");
        userRepository.save(user);

        user.setName("Nani updated");
        userRepository.updateUser(user, user.getId());

        Optional<User> updatedUser = userRepository.findById(user.getId());

        assertEquals("Nani updated", updatedUser.get().getName());

    }

    @Test
    public void shouldDeleteUser() {

        User user = new User(5l, "Test");
        userRepository.save(user);
        assertTrue(userRepository.existsById(user.getId()));

        userRepository.deleteById(user.getId());
        assertFalse(userRepository.existsById(user.getId()));

    }

    @Test
    public void shouldVerifyIfUserExists() {

        User user = new User(1L, "Maria");
        userRepository.save(user);

        assertTrue(userRepository.existsById(user.getId()));

    }

}
