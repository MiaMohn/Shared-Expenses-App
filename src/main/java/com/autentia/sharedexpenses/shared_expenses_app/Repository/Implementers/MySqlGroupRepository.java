package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Group;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IGroupRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers.GroupRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

//Group Repository class, not finished

@Component
public class MySqlGroupRepository implements IGroupRepository {

    //Queries:
    public static final String FIND_ALL_GROUPS = "SELECT * FROM FriendGroup";
    public static final String FIND_GROUP_BY_ID = "SELECT * FROM FriendGroup WHERE id = ?";
    public static final String INSERT_GROUP = "INSERT INTO FriendGroup(id, title, description) VALUES (?, ?, ?)";
    public static final String UPDATE_GROUP = "UPDATE FriendGroup SET title = ?, description = ? WHERE id = ? ";
    public static final String DELETE_GROUP_BY_ID = "DELETE FROM FriendGroup WHERE id = ? ";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MySqlGroupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(FIND_ALL_GROUPS, new GroupRowMapper());
    }

    @Override
    public void save(Group group) {
        jdbcTemplate.update(INSERT_GROUP, group.getId(), group.getTitle(), group.getDescription());
    }

    @Override
    public Optional<Group> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_GROUP_BY_ID, new GroupRowMapper(), new Object[]{id}));
    }

    @Override
    public void update(Group group, int id) {
        jdbcTemplate.update(UPDATE_GROUP, group.getTitle(), group.getDescription(), id);
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE_GROUP_BY_ID, id);
    }
}
