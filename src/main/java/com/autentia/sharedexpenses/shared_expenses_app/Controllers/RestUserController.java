package com.autentia.sharedexpenses.shared_expenses_app.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.UserRequest;
import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response.UserResponse;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
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

        return users.stream() //Transformar List<User> en List<UserResponse>
                .map(UserResponse::new)
                .collect(Collectors.toList());

    }

    //Create a new user:

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){

        User user = new User(userRequest.getId(), userRequest.getName());
        this.userService.createUser(user);
        return ResponseEntity.ok("User created successfully.");

    }

    //Find a user by their id:

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id){

        return userService.getUserById(id) //Transformar List<User> en List<UserResponse>
                .map(UserResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Update user:

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest request, @PathVariable("id") long id){

        User userToUpdate = new User(id, request.getName());

        try {
            User user = userService.updateUser(userToUpdate, id);
            return ResponseEntity.ok(new UserResponse(user));
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

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
