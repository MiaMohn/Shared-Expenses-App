package com.autentia.sharedexpenses.shared_expenses_app.TestsIntegrados.Flyway.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.UserRequest;
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
public class FlywayUserControllerIT {

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
    public void shouldGetUsers() {

        given().contentType(ContentType.JSON)
                .when()
                .get("/user")
                .then()
                .statusCode(200)
                .body(".", hasSize(3));
    }

    @Test
    @Order(2)
    public void shouldCreateUser() {

        UserRequest user = new UserRequest(4L, "Mochi");

        given().contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/user")
                .then()
                .statusCode(201);

    }

    @Test
    @Order(3)
    public void shouldGetUserById() {

        given().contentType(ContentType.JSON)
                .pathParam("id", 1L)
                .when()
                .get("/user/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Maria"));
    }

    @Test
    @Order(4)
    public void shouldUpdateUser() {

        UserRequest user = new UserRequest(1L, "Maria updated");

        given().contentType(ContentType.JSON)
                .pathParam("id", 1L)
                .body(user)
                .when()
                .put("/user/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Maria updated"));
    }

    @Test
    @Order(5)
    public void shouldDeleteUser() {

        given().contentType(ContentType.JSON)
                .pathParam("id", 1L)
                .when()
                .delete("/user/{id}")
                .then()
                .statusCode(200)
                .body(equalTo("User deleted successfully"));

    }


}
