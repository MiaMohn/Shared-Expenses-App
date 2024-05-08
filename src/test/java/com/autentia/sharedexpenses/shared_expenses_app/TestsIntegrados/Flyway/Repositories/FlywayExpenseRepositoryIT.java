package com.autentia.sharedexpenses.shared_expenses_app.TestsIntegrados.Flyway.Repositories;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.MySqlExpenseRepository;
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

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlywayExpenseRepositoryIT {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    private MySqlExpenseRepository expenseRepository;

    private Timestamp date = new Timestamp(System.currentTimeMillis());

    @BeforeAll
    static void beforeAll(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Order(1)
    public void shouldFindAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        assertEquals(3, expenses.size());
    }

    @Test
    @Order(2)
    public void shouldSaveExpense() {

        Expense expense = new Expense(4L, "Compra", 54.67, new User(1L, "Maria"), date);
        expenseRepository.save(expense);

        assertTrue(expenseRepository.existsById(expense.getId()));
    }

    @Test
    @Order(3)
    public void shouldFindExpenseById() {
        Optional<Expense> createdExpense = expenseRepository.findById(1L);
        assertTrue(createdExpense.isPresent());
    }

    @Test
    @Order(4)
    public void shouldFindExpensesByUserId() {
        List<Expense> expensesByUser = expenseRepository.findByUserId(1L);
        assertEquals(2, expensesByUser.size());
    }

    @Test
    @Order(5)
    public void shouldUpdateExpense() {

        Expense expense = new Expense(1L, "Expense updated", 25.50, new User(1L, "Maria"), date);

        expenseRepository.update(expense, expense.getId());

        Optional<Expense> updatedExpense = expenseRepository.findById(expense.getId());

        assertEquals("Expense updated", updatedExpense.get().getDescription());
        assertEquals(25.50F, updatedExpense.get().getAmount());

    }

    @Test
    @Order(6)
    public void shouldDeleteExpense() {
        expenseRepository.deleteById(4L);
        assertFalse(expenseRepository.existsById(4L));
    }

    @Test
    @Order(7)
    public void shouldVerifyIfExpenseExists() {
        assertTrue(expenseRepository.existsById(1L));
    }

}
