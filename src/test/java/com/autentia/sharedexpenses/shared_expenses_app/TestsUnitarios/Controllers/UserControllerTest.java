package com.autentia.sharedexpenses.shared_expenses_app.TestsUnitarios.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.UserRequest;
import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response.UserResponse;
import com.autentia.sharedexpenses.shared_expenses_app.Controllers.RestUserController;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Services.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RestUserController userController;


    @Test
    public void shouldGetUsers() {

        //Given:

        final List<User> users = Arrays.asList(
                new User (1L, "Belen"),
                new User(2L, "Juan")
        );

        final List<UserResponse> expected = Arrays.asList(
                new UserResponse(new User (1L, "Belen")),
                new UserResponse(new User(2L, "Juan"))
        );

        //When:

        when(userService.getUsers()).thenReturn(users);

        List<UserResponse> actual = userController.getUsers();

        verify(userService, times(1)).getUsers();

        assertThat(actual).isEqualTo(expected);

    }


    @Test
    public void shouldCreateUser() {

        //Given:

        final UserRequest request = new UserRequest(1L, "Maria");
        final User expected = new User(1L, "Maria");

        //When:

        userController.createUser(request);

        //Then:

        verify(userService, times(1)).createUser(expected);
    }

    @Test
    public void shouldGetUserById() {

        //Given:

        final long id = 1L;
        final User user = new User(id, "Maria");
        final UserResponse expected = new UserResponse(new User(id, "Maria"));

        //When:

        when(userService.getUserById(id)).thenReturn(Optional.of(user));

        UserResponse actual = userController.getUserById(id);

        //Then:

        verify(userService, times(1)).getUserById(id);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldUpdateUser() {

        //Given:

        final long id = 1L;
        final UserRequest request = new UserRequest(id, "Maria G.");
        final User expected = new User(id, "Maria G.");

        //When:

        when(userService.updateUser(any(User.class), eq(id))).thenReturn(expected);

        UserResponse actual = userController.updateUser(request, id);

        //Then:

        verify(userService, times(1)).updateUser(any(User.class), eq(id));

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getId(), actual.getId());

    }

    @Test
    public void shouldDeleteUser() {

        //Given:

        final long id = 1L;

        //When:

        when(userService.deleteUser(id)).thenReturn(true);

        String response = userController.deleteUser(id);

        //Then:

        verify(userService, times(1)).deleteUser(id);

        assertEquals("User deleted successfully", response);

    }

}

