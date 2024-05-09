package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories.Adapters;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.UserEntity;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IUserRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories.JpaUserRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories.Mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaUserRepositoryAdapter implements IUserRepository {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(UserMapper.INSTANCE::toDomainFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void save(User user) {
        jpaUserRepository.save(UserMapper.INSTANCE.toEntityFromDomain(user));
    }

    @Override
    public Optional<User> findById(long id) {
        Optional<UserEntity> userEntityOptional = jpaUserRepository.findById(id);
        return userEntityOptional.map(UserMapper.INSTANCE::toDomainFromEntity);
    }

    @Override
    public void updateUser(User user, long id) {
        jpaUserRepository.save(UserMapper.INSTANCE.toEntityFromDomain(user));
    }

    @Override
    public void deleteById(long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return jpaUserRepository.existsById(id);
    }
}
