package com.autentia.sharedexpenses.shared_expenses_app.TestsIntegrados;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private List<User> users;

    @BeforeEach
    void setUp() {

        users = Arrays.asList(
                new User(1L, "Maria"),
                new User(2L, "Belen"),
                new User(3L, "Juan")
        );

        when(userService.getUsers()).thenReturn(users);
        users.forEach(user -> when(userService.getUserById(user.getId())).thenReturn(Optional.of(user)));   //Lambda foreach
        when(userService.updateUser(any(User.class), eq(1L))).thenReturn(new User(1L, "Updated name"));
        when(userService.deleteUser(1L)).thenReturn(true);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .defaultRequest(get("/").contentType(MediaType.APPLICATION_JSON))
                .build();

    }

    @Test
    void shouldGetUsers() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("Maria"))
                .andExpect(jsonPath("$[1].name").value("Belen"))
                .andExpect(jsonPath("$[2].name").value("Juan"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        mockMvc.perform(post("/user")
                .content("{\"name\": \"Nani\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetUserById() throws Exception {

        for (User user : users) {   //Could use lambda but requires try/catch
            mockMvc.perform(get("/user/{id}", user.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(user.getName()));
        }
    }

    @Test
    void shouldUpdateUser() throws Exception {
        mockMvc.perform(put("/user/{id}", 1L)
                .content("{\"name\": \"Updated name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated name"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

}