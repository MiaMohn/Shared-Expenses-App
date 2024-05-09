package com.autentia.sharedexpenses.shared_expenses_app.TestsUnitarios.Services;

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

import static org.assertj.core.api.Assertions.assertThat;
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

        //Given:

        List<User> users = Arrays.asList(
                new User(1L, "Jasmine"),
                new User(2L, "Nani")
        );

        //When:

        when(userRepository.findAll()).thenReturn(users);

        List<User> userResponse = userService.getUsers();

        //Then:

        verify(userRepository, Mockito.times(1)).findAll();

        assertThat(userResponse).isEqualTo(users);

    }


    @Test
    public void shouldUseRepositoryWhenCreatingUser() {

        //Given:

        User userRequest = new User(10L,"Jasmine");
        User userExpected = new User(10L,"Jasmine" );

        //When:

        userService.createUser(userRequest);

        //Then:

        verify(userRepository, Mockito.times(1)).save(userExpected);

    }


    @Test
    public void shouldUseRepositoryWhenGettingUserById() {

        //Given:

        long id = 2L;
        User userExpected = new User(2L, "Nani");

        //When:

        when(userRepository.findById(id)).thenReturn(Optional.of(userExpected));
        when(userRepository.existsById(id)).thenReturn(true);

        Optional<User> result = userService.getUserById(id);

        //Then:

        assertTrue(result.isPresent(), "User must exist");
        assertEquals(userExpected, result.get());

    }

    @Test
    public void shouldUseRepositoryWhenUpdatingUser() {

        //Given:

        long id = 1L;
        User user = new User(id, "Nani");
        User expected = new User(id, "Jasmine");

        //When:

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsById(id)).thenReturn(true);

        User updatedUser = userService.updateUser(expected, id);

        //Then:

        verify(userRepository).findById(id);
        verify(userRepository).updateUser(expected, id);

        assertEquals("Jasmine", updatedUser.getName());

    }

    @Test
    public void shouldUseRepositoryWhenDeletingUser() {

        //Given:

        long id = 1L;

        //When:

        doNothing().when(userRepository).deleteById(id);
        when(userRepository.existsById(id)).thenReturn(true);

        Boolean result = userService.deleteUser(id);

        //Then:

        verify(userRepository, Mockito.times(1)).deleteById(id);

        assertTrue(result, "User should be deleted successfully");
    }


}
