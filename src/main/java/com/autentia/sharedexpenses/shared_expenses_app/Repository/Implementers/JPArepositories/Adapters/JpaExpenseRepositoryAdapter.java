package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories.Adapters;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.ExpenseEntity;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IExpenseRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories.JpaExpenseRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories.Mappers.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaExpenseRepositoryAdapter implements IExpenseRepository {

    @Autowired
    private JpaExpenseRepository jpaExpenseRepository;

    @Override
    public List<Expense> findAll() {
         List<Expense> expenses = jpaExpenseRepository.findAll().stream()
                .map(ExpenseMapper.INSTANCE::toDomainFromEntity)
                .collect(Collectors.toList());

         for (Expense expense : expenses) {
             expense.getUser().setName(jpaExpenseRepository.findExpenseFriendNameById(expense.getId()));
         }

         return expenses;
    }

    @Override
    public void save(Expense expense) {
        jpaExpenseRepository.save(ExpenseMapper.INSTANCE.toEntityFromDomain(expense));
    }

    @Override
    public Optional<Expense> findById(long id) {
        Optional<ExpenseEntity> expenseEntityOptional = jpaExpenseRepository.findById(id);
        Optional<Expense> expense = expenseEntityOptional.map(ExpenseMapper.INSTANCE::toDomainFromEntity);

        expense.get().getUser().setName(jpaExpenseRepository.findExpenseFriendNameById(expense.get().getId()));

        return expense;
    }

    @Override
    public List<Expense> findByUserId(long id) {

        List<ExpenseEntity> allExpenses = jpaExpenseRepository.findAll();

        List<ExpenseEntity> userExpenses = new ArrayList<>();

        for (ExpenseEntity expense : allExpenses) {
            if (expense.getUser_id() == id) {
                userExpenses.add(expense);
            }
        }

        List<Expense> expenses = userExpenses.stream()
                .map(ExpenseMapper.INSTANCE::toDomainFromEntity)
                .collect(Collectors.toList());

        for (Expense expense : expenses) {
            expense.getUser().setName(jpaExpenseRepository.findExpenseFriendNameById(expense.getId()));
        }

        return expenses;
    }

    @Override
    public void update(Expense expense, long id) {
        jpaExpenseRepository.save(ExpenseMapper.INSTANCE.toEntityFromDomain(expense));
    }

    @Override
    public void deleteById(long id) {
        jpaExpenseRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        return jpaExpenseRepository.existsById(id);
    }
}
