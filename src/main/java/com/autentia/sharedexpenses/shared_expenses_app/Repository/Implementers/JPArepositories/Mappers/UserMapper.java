package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories.Mappers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response.UserResponse;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toDomainFromEntity(UserEntity userEntity);

    UserResponse toResponseFromDomain(User user);

    UserEntity toEntityFromDomain(User user);

}
