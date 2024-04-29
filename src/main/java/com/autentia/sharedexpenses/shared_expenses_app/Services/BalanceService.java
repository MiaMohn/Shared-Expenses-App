package com.autentia.sharedexpenses.shared_expenses_app.Services;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Utilities.BalanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BalanceService {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private BalanceCalculator balanceCalculator;


    //Function that obtains a map with the balance of each user

    public Map<String, Double> calculateUsersBalance() {

        List<Expense> expenses = expenseService.getExpenses();
        List<User> users = userService.getUsers();

        double totalExpense = balanceCalculator.calculateTotalAmountExpenses(expenses);
        int totalUsers = balanceCalculator.calculateTotalUsers(users);
        double amountPerUser = balanceCalculator.calculateAmountPerUser(totalExpense, totalUsers);

        return balanceCalculator.calculateBalance(users, expenses, amountPerUser);

    }





    /*public Map<User, Double> calculateUsersBalance() {

        List<Expense> expenses = expenseService.getExpenses();
        List<User> users = userService.getUsers();

        double totalExpense = balanceCalculator.calculateTotalAmountExpenses(expenses);
        int totalUsers = balanceCalculator.calculateTotalUsers(users);
        double amountPerUser = balanceCalculator.calculateAmountPerUser(totalExpense, totalUsers);

        return balanceCalculator.calculateBalance(users, expenses, amountPerUser);

    }*/

}
