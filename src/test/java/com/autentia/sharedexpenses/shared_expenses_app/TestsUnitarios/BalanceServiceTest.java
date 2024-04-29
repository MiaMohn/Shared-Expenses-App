package com.autentia.sharedexpenses.shared_expenses_app.TestsUnitarios;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Services.BalanceService;
import com.autentia.sharedexpenses.shared_expenses_app.Services.ExpenseService;
import com.autentia.sharedexpenses.shared_expenses_app.Services.UserService;
import com.autentia.sharedexpenses.shared_expenses_app.Utilities.BalanceCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @Mock
    private ExpenseService expenseService;

    @Mock
    private UserService userService;

    @Mock
    private BalanceCalculator balanceCalculator;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    public void shouldCalculateUsersBalance() {

        List<Expense> expenses = new ArrayList<>();
        List<User> users = Arrays.asList(
                new User(1L, "Jasmine"),
                new User(2L, "Nani")
        );

        double totalExpense = 163.40;
        int totalUsers = users.size();
        double amountPerUser = totalExpense / totalUsers;

        Map<String, Double> expectedBalance = new HashMap<>();
        expectedBalance.put("Jasmine", amountPerUser);
        expectedBalance.put("Nani", amountPerUser);

        Mockito.when(expenseService.getExpenses()).thenReturn(expenses);
        Mockito.when(userService.getUsers()).thenReturn(users);
        Mockito.when(balanceCalculator.calculateTotalAmountExpenses(expenses)).thenReturn(totalExpense);
        Mockito.when(balanceCalculator.calculateTotalUsers(users)).thenReturn(totalUsers);
        Mockito.when(balanceCalculator.calculateAmountPerUser(totalExpense, totalUsers)).thenReturn(amountPerUser);
        Mockito.when(balanceCalculator.calculateBalance(users, expenses, amountPerUser)).thenReturn(expectedBalance);

        Map<String, Double> balance = balanceService.calculateUsersBalance();

        Mockito.verify(expenseService, Mockito.times(1)).getExpenses();
        Mockito.verify(userService, Mockito.times(1)).getUsers();
        Mockito.verify(balanceCalculator, Mockito.times(1)).calculateTotalAmountExpenses(expenses);
        Mockito.verify(balanceCalculator, Mockito.times(1)).calculateTotalUsers(users);
        Mockito.verify(balanceCalculator, Mockito.times(1)).calculateAmountPerUser(totalExpense, totalUsers);
        Mockito.verify(balanceCalculator, Mockito.times(1)).calculateBalance(users, expenses, amountPerUser);

        assertEquals(expectedBalance, balance);

    }

}
