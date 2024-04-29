package com.autentia.sharedexpenses.shared_expenses_app.TestsUnitarios;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IUserRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldUseRepositoryWhenGettingUsersList() {

        List<User> users = Arrays.asList(
                new User(1L, "Jasmine"),
                new User(2L, "Nani")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> userResponse = userService.getUsers();

        verify(userRepository, Mockito.times(1)).findAll();

        assertEquals(2, userResponse.size());
        assertEquals("Jasmine", userResponse.get(0).getName());
        assertEquals("Nani", userResponse.get(1).getName());

    }


    @Test
    public void shouldUseRepositoryWhenCreatingUser() {

        User userRequest = new User(10L,"Jasmine");
        User userExpected = new User(10L,"Jasmine" );

        userService.createUser(userRequest);

        verify(userRepository, Mockito.times(1)).save(userExpected);

    }


    @Test
    public void shouldUseRepositoryWhenGettingUserById() {

        long id = 2L;
        User userExpected = new User(2L, "Nani");

        when(userRepository.findById(id)).thenReturn(Optional.of(userExpected));

        Optional<User> result = userService.getUserById(id);

        assertTrue(result.isPresent(), "User must exist");
        assertEquals(userExpected, result.get());

    }

    @Test
    public void shouldUseRepositoryWhenUpdatingUser() {

        long id = 2L;
        User user = new User(2L, "Nani");
        User expected = new User(2L, "Jasmine");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User updatedUser = userService.updateUser(expected, id);

        verify(userRepository).findById(id);
        verify(userRepository).updateUser(any(User.class), eq(id));

        assertEquals("Jasmine", updatedUser.getName());

    }

    @Test
    public void shouldUseRepositoryWhenDeletingUser() {

        long id = 1L;

        doNothing().when(userRepository).deleteById(id);

        Boolean result = userService.deleteUser(id);

        verify(userRepository, Mockito.times(1)).deleteById(id);

        assertTrue(result, "User should be deleted successfully");
    }


}
