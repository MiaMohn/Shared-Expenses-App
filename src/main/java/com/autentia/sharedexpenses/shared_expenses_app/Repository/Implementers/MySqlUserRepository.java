package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IUserRepository;

import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MySqlUserRepository implements IUserRepository {

    public static final String FIND_ALL_USERS = "SELECT * FROM User";
    public static final String FIND_USER_BY_ID = "SELECT * FROM User WHERE id = ?";
    public static final String INSERT_USER = "INSERT INTO User(id, name) VALUES (?, ?)";
    public static final String UPDATE_USER = "UPDATE User SET name = ? WHERE id = ? ";
    public static final String DELETE_USER_BY_ID = "DELETE FROM User WHERE id = ? ";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MySqlUserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_USERS, new UserRowMapper());
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update(INSERT_USER, user.getId(), user.getName());
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER_BY_ID, new UserRowMapper(), new Object[]{id}));
    }

    @Override
    public void updateUser(User user, long id) {
        jdbcTemplate.update(UPDATE_USER, user.getName(), id);
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_USER_BY_ID, id);
    }
}
