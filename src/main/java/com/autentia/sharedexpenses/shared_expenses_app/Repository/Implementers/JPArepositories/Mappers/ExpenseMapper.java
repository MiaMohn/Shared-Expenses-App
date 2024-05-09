package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories.Mappers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response.ExpenseResponse;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    Expense toDomainFromEntity(ExpenseEntity expenseEntity);

    ExpenseResponse toResponseFromDomain(Expense expense);

    ExpenseEntity toEntityFromDomain(Expense expense);

}
