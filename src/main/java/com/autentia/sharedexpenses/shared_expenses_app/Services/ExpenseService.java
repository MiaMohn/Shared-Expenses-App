package com.autentia.sharedexpenses.shared_expenses_app.Services;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IExpenseRepository;

import com.autentia.sharedexpenses.shared_expenses_app.Repository.IUserRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Services.Exceptions.ExpenseNotFoundException;
import com.autentia.sharedexpenses.shared_expenses_app.Services.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final IExpenseRepository expenseRepository;
    private final IUserRepository userRepository;

    @Autowired
    public ExpenseService(IExpenseRepository expenseRepository, IUserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    //List all expenses:

    public List<Expense> getExpenses(){
        return expenseRepository.findAll();
    }

    //Create a new expense:

    public void createExpense(long userId, String description, double amount) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount can't be 0 or less");
        }

        if (description.isEmpty()) {
            throw new IllegalArgumentException("Must provide a description");
        }

        else {
            User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            Expense expense = new Expense(null, description, amount, user, currentTimestamp);
            expenseRepository.save(expense);
        }
    }

    //Find an expense by its id:

    public Optional<Expense> getExpenseById(long id){

        if (expenseRepository.existsById(id)) {
            return expenseRepository.findById(id);
        } else {
            throw new ExpenseNotFoundException();
        }
    }

    //Find an expense by its user id:

    public List<Expense> getExpenseByUserId(long user_id) {

        if (userRepository.existsById(user_id)) {
            return expenseRepository.findByUserId(user_id);
        } else {
            throw new UserNotFoundException();
        }
    }

    //Update expense:

    public Expense updateExpense(long userId, long expenseId, String description, double amount){

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        if (!expenseRepository.existsById(expenseId)) {
            throw new ExpenseNotFoundException();
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount can't be 0 or less");
        }

        if (description.isEmpty()) {
            throw new IllegalArgumentException("Must provide a description");
        }

        else {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            expense.setDescription(description);
            expense.setAmount(amount);
            expense.setUser(user);
            expense.setExpenseDate(currentTimestamp);

            expenseRepository.update(expense, expenseId);
            return expense;
        }

    }

    //Delete expense:

    public Boolean deleteExpense(long id){

        if (expenseRepository.existsById(id)) {

            try {
                expenseRepository.deleteById(id);
                return true;

            } catch (Exception e){
                return false;
            }
        } else {
            throw new ExpenseNotFoundException();
        }

    }
}
