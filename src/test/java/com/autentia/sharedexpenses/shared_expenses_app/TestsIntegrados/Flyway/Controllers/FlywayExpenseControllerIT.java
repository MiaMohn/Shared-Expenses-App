package com.autentia.sharedexpenses.shared_expenses_app.TestsIntegrados.Flyway.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.ExpenseRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlywayExpenseControllerIT {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeAll
    static void beforeAll(@Autowired Flyway flyway, @LocalServerPort Integer port) {
        RestAssured.baseURI = "http://localhost:" + port;
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Order(1)
    public void shouldGetExpenses() {

        given().contentType(ContentType.JSON)
                .when()
                .get("/expense")
                .then()
                .statusCode(200)
                .body(".", hasSize(3));
    }

    @Test
    @Order(2)
    public void shouldCreateExpense() {

        ExpenseRequest expense = new ExpenseRequest(4L, "Compra", 100, 1L);

        given().contentType(ContentType.JSON)
                .body(expense)
                .when()
                .post("/expense")
                .then()
                .statusCode(201);

    }

    @Test
    @Order(3)
    public void shouldGetExpenseById() {

        given().contentType(ContentType.JSON)
                .pathParam("id", 1L)
                .when()
                .get("/expense/{id}")
                .then()
                .statusCode(200)
                .body("description", equalTo("Compra de material 1"))
                .body("amount", equalTo(15F))
                .body("userName", equalTo("Maria"));
    }

    @Test
    @Order(4)
    public void shouldGetExpenseByUserId() {

        given().contentType(ContentType.JSON)
                .pathParam("user_id", 2L)
                .when()
                .get("/expense/user/{user_id}")
                .then()
                .statusCode(200)
                .body(".", hasSize(1));
    }

    @Test
    @Order(5)
    public void shouldUpdateExpense() {

        ExpenseRequest expense = new ExpenseRequest(1L, "Description updated", 15.00, 1L);

        given().contentType(ContentType.JSON)
                .pathParam("id", 1L)
                .body(expense)
                .when()
                .put("/expense/{id}")
                .then()
                .statusCode(200)
                .body("description", equalTo("Description updated"));
    }

    @Test
    @Order(6)
    public void shouldDeleteExpense() {

        given().contentType(ContentType.JSON)
                .pathParam("id", 1L)
                .when()
                .delete("/expense/{id}")
                .then()
                .statusCode(200)
                .body(equalTo("Expense deleted successfully"));

    }
}
