package com.autentia.sharedexpenses.shared_expenses_app.Repository;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IExpenseRepository {

    List<Expense> findAll();    //Desc order

    void save(Expense expense);

    Optional<Expense> findById(long id);

    List<Expense> findByUserId(long id);

    void update(Expense expense, long id);

    void deleteById(long id);

    boolean existsById(long id);
}
