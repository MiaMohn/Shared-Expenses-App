package com.autentia.sharedexpenses.shared_expenses_app.Repository;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Group;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGroupRepository {

    List<Group> findAll();

    void save(Group group);

    Optional<Group> findById(int id);

    void update(Group group, int id);

    void deleteById(int id);
}
