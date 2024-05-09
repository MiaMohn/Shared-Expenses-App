package com.autentia.sharedexpenses.shared_expenses_app.TestsUnitarios.Services;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IExpenseRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IUserRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Services.ExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private IExpenseRepository expenseRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private ExpenseService expenseService;


    @Test
    public void shouldUseRepositoryWhenGettingExpensesList() {

        //Given:

        Timestamp date = new Timestamp(System.currentTimeMillis());
        List<Expense> expenses = Arrays.asList(
                new Expense(1L, "Cena", 54.00, new User(1L, "Nani"), date),
                new Expense(2L, "Ropa", 38.70, new User(1L, "Nani"), date)
        );

        //When:

        when(expenseRepository.findAll()).thenReturn(expenses);

        List<Expense> result = expenseService.getExpenses();

        //Then:

        verify(expenseRepository, Mockito.times(1)).findAll();

        assertEquals(expenses.size(), result.size());
        assertEquals(expenses, result);

    }

    @Test
    public void shouldUseRepositoryWhenCreatingExpense() {

        //Given:

        User user = new User(1L, "Jasmine");
        long userId = 1L;
        String description = "Cena";
        double amount = 60.00;

        //When:

        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        expenseService.createExpense(userId, description, amount);

        //Then:

        verify(userRepository).existsById(1L);
        verify(userRepository).findById(1L);
        verify(expenseRepository).save(any(Expense.class));

    }

    @Test
    public void shouldUseRepositoryWhenGettingExpenseById() {

        //Given:

        Timestamp date = new Timestamp(System.currentTimeMillis());
        long id = 2L;
        Expense expected = new Expense(2L, "Cena", 60.00, new User(1L, "Nani"), date);

        //When:

        when(expenseRepository.existsById(id)).thenReturn(true);
        when(expenseRepository.findById(id)).thenReturn(Optional.of(expected));

        Optional<Expense> result = expenseService.getExpenseById(id);

        //Then:

        verify(expenseRepository).findById(id);

        assertTrue(result.isPresent(), "Expense must exist");
        assertEquals(expected, result.get());

    }

    @Test
    public void shouldUseRepositoryWhenGettingExpenseByUserId() {

        //Given:

        Timestamp date = new Timestamp(System.currentTimeMillis());
        long id = 2L;
        List<Expense> expenses = Arrays.asList(
                new Expense(1L, "Cena", 54.00, new User(1L, "Nani"), date),
                new Expense(2L, "Ropa", 38.70, new User(1L, "Nani"), date),
                new Expense(3L, "Museo", 12.00, new User(2L, "Jasmine"), date)
        );

        //When:

        when(userRepository.existsById(id)).thenReturn(true);
        when(expenseRepository.findByUserId(id)).thenReturn(expenses);

        List<Expense> result = expenseService.getExpenseByUserId(id);

        //Then:

        verify(userRepository).existsById(id);
        verify(expenseRepository).findByUserId(id);

        assertEquals(expenses, result);
    }

    @Test
    public void shouldUseRepositoryWhenUpdatingExpense() {

        //Given:

        Timestamp date = new Timestamp(System.currentTimeMillis());
        long id = 1L;
        User user = new User(id, "Nani");
        Expense existingExpense = new Expense(id, "Ropa", 54.00, user, date);
        String newDescription = "Cena";
        double newAmount = 60.00;

        //When:

        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(expenseRepository.existsById(id)).thenReturn(true);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(expenseRepository.findById(id)).thenReturn(Optional.of(existingExpense));

        Expense updatedExpense = new Expense(id, newDescription, newAmount, user, date);
        doNothing().when(expenseRepository).update(any(Expense.class), eq(id));

        Expense result = expenseService.updateExpense(user.getId(), id, newDescription, newAmount);

        //Then:

        verify(userRepository).existsById(user.getId());
        verify(userRepository).findById(user.getId());
        verify(expenseRepository).existsById(id);
        verify(expenseRepository).findById(id);
        verify(expenseRepository).update(any(Expense.class), eq(id));

        assertEquals(updatedExpense.getDescription(), result.getDescription());
        assertEquals(updatedExpense.getAmount(), result.getAmount());
        assertEquals(updatedExpense.getUser(), result.getUser());
    }

    @Test
    public void shouldUseRepositoryWhenDeletingExpense() {

        //Given:

        long id = 1L;

        //When:

        when(expenseRepository.existsById(id)).thenReturn(true);
        doNothing().when(expenseRepository).deleteById(id);

        Boolean result = expenseService.deleteExpense(id);

        //Then:

        verify(expenseRepository, Mockito.times(1)).deleteById(id);

        assertTrue(result, "Expense should be deleted successfully");

    }


}

