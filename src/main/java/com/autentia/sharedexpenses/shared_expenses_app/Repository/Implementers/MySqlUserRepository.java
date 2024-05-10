package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.UserEntity;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IUserRepository;

import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MySqlUserRepository implements IUserRepository {

    //Queries:
    public static final String FIND_ALL_USERS = "SELECT * FROM user";
    public static final String FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    public static final String INSERT_USER = "INSERT INTO user(name) VALUES (?)";
    public static final String UPDATE_USER = "UPDATE user SET name = ? WHERE id = ? ";
    public static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE id = ? ";
    public static final String DOES_USER_EXISTS = "SELECT COUNT(*) FROM user WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MySqlUserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {

        List<UserEntity> userEntityList = jdbcTemplate.query(FIND_ALL_USERS, new UserRowMapper());
        return toUser(userEntityList);

    }

    @Override
    public void save(User user) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_USER, new String[]{"id"});
            ps.setString(1, user.getName());
            return ps;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

    }

    @Override
    public Optional<User> findById(long id) {

        Optional<UserEntity> userEntity = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER_BY_ID, new UserRowMapper(), id));
        return userEntity.map(this::toUser);
    }

    @Override
    public void updateUser(User user, long id) {
        jdbcTemplate.update(UPDATE_USER, user.getName(), id);
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_USER_BY_ID, id);
    }

    @Override
    public boolean existsById(long id) {
        return jdbcTemplate.queryForObject(DOES_USER_EXISTS, Integer.class, id) > 0;
    }


    //Conversion functions
    private User toUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName());
    }

    private List<User> toUser(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::toUser)
                .collect(Collectors.toList());
    }
}
