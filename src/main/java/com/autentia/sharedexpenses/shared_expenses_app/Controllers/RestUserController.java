package com.autentia.sharedexpenses.shared_expenses_app.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.UserRequest;
import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response.UserResponse;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Services.Exceptions.UserNotFoundException;
import com.autentia.sharedexpenses.shared_expenses_app.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }


    //List all users:

    @GetMapping
    public List<UserResponse> getUsers(){

        List<User> users = this.userService.getUsers();

        return users.stream() //Convert List<User> in List<UserResponse>
                .map(UserResponse::new)
                .collect(Collectors.toList());

    }

    //Create a new user:

    @PostMapping
    public void createUser(@RequestBody UserRequest userRequest){

        User user = new User(userRequest.getId(), userRequest.getName());
        this.userService.createUser(user);

    }

    //Find a user by their id:

    @GetMapping(path = "/{id}")
    public UserResponse getUserById(@PathVariable("id") long id){
        return userService.getUserById(id).map(UserResponse::new).orElseThrow(UserNotFoundException::new);
    }

    //Update user:

    @PutMapping(path = "/{id}")
    public UserResponse updateUser(@RequestBody UserRequest request, @PathVariable("id") long id){

        User userToUpdate = new User(id, request.getName());
        User user = userService.updateUser(userToUpdate, id);

        return new UserResponse(user);

    }

    //Delete user:

    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable("id") long id){

        boolean deleted = this.userService.deleteUser(id);

        if (deleted){
            return "User deleted successfully";
        } else {
            return "Failed to delete user";
        }
    }

}
