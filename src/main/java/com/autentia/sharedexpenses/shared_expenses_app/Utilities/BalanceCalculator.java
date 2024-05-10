package com.autentia.sharedexpenses.shared_expenses_app.Utilities;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BalanceCalculator {

    //Function that calculates the total amount of shared expenses

    public double calculateTotalAmountExpenses(List<Expense> expenses) {

        double totalExpense = 0;

        for (Expense expense : expenses) {
            totalExpense += expense.getAmount();
        }

        return totalExpense;

    }

    //Function that returns the total number of users

    public int calculateTotalUsers(List<User> users) {
        return users.size();
    }

    //Function that calculates the amount each user has to pay

    public double calculateAmountPerUser(double totalAmount, int totalUsers) {
        return totalAmount / totalUsers;
    }

    //Function that calculates the balance per user

    public Map<String, Double> calculateBalance(List<User> users, List<Expense> expenses, double amountPerUser) {

        Map<String, Double> balance = new HashMap<>();
        Map<String, Double> totalExpensePerUser = new HashMap<>();

        for (User user : users) {
            totalExpensePerUser.put(user.getName(), 0.0);
        }

        for (Expense expense : expenses) {
            String userName = expense.getUser().getName();
            totalExpensePerUser.merge(userName, expense.getAmount(), Double::sum);
        }

        for (User user : users) {

            String userName = user.getName();
            double userExpense = totalExpensePerUser.get(userName);
            Double amount = BigDecimal.valueOf((userExpense - amountPerUser)).setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            balance.put(userName, amount);
        }

        return balance;

    }

}