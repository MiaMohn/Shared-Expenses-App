package com.autentia.sharedexpenses.shared_expenses_app.Repository;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository{

    List<User> findAll();

    void save(User user);

    Optional<User> findById(long id);

    void updateUser(User user, long id);

    void deleteById(long id);
}
