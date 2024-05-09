package com.autentia.sharedexpenses.shared_expenses_app.Services;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IUserRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Services.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //List all users:

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    //Create a new user:

    public void createUser(User user) {
        this.userRepository.save(user);
    }

    //Find a user by their id:

    public Optional<User> getUserById(long id) {

        if (userRepository.existsById(id)) {
            return userRepository.findById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    //Update user:

    public User updateUser(User user, long id){

        if (userRepository.existsById(id)) {

            User userToUpdate = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

            userToUpdate.setName(user.getName());
            userRepository.updateUser(userToUpdate, id);
            return userToUpdate;
        } else {
            throw new UserNotFoundException();
        }
    }

    //Delete user:

    public Boolean deleteUser(long id){

        if (userRepository.existsById(id)) {

            try {
                userRepository.deleteById(id);
                return true;

            } catch (Exception e){
                return false;
            }

        } else {
            throw new UserNotFoundException();
        }

    }

}
