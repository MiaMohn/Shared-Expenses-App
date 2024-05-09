package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories.Mappers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response.ExpenseResponse;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(source = "user_id", target = "user.id")
    Expense toDomainFromEntity(ExpenseEntity expenseEntity);

    ExpenseResponse toResponseFromDomain(Expense expense);

    @Mapping(source = "user.id", target = "user_id")
    ExpenseEntity toEntityFromDomain(Expense expense);

}
