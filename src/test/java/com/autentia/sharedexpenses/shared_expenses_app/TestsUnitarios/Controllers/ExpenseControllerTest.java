package com.autentia.sharedexpenses.shared_expenses_app.TestsUnitarios.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.ExpenseRequest;
import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response.ExpenseResponse;
import com.autentia.sharedexpenses.shared_expenses_app.Controllers.RestExpenseController;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Services.ExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {

    @Mock
    ExpenseService expenseService;

    @InjectMocks
    RestExpenseController expenseController;

    @Test
    public void shouldGetExpenses() {

        //Given:

        Timestamp date = new Timestamp(System.currentTimeMillis());

        final List<Expense> expenses = Arrays.asList(
                new Expense(1L, "Cena", 100, (new User(1L, "Belen")), date),
                new Expense(2L, "Taxi", 10, (new User(2L, "Juan")), date),
                new Expense(3L, "Compra", 54.60, (new User(3L, "Maria")), date)
        );

        final List<ExpenseResponse> expected = Arrays.asList(
                new ExpenseResponse(new Expense(1L, "Cena", 100, (new User(1L, "Belen")), date)),
                new ExpenseResponse(new Expense(2L, "Taxi", 10, (new User(2L, "Juan")), date)),
                new ExpenseResponse(new Expense(3L, "Compra", 54.60, (new User(3L, "Maria")), date))
        );

        //When:

        when(expenseService.getExpenses()).thenReturn(expenses);

        List<ExpenseResponse> actual = expenseController.getExpenses();

        verify(expenseService, times(1)).getExpenses();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldCreateExpense() {

        //Given:

        final ExpenseRequest request = new ExpenseRequest(1L, "Cena", 100, 1L);

        //When:

        doNothing().when(expenseService).createExpense(request.getUser_id(), request.getDescription(), request.getAmount());

        expenseController.createExpense(request);

        verify(expenseService, times(1)).createExpense(request.getUser_id(), request.getDescription(), request.getAmount());
    }

    @Test
    public void shouldGetExpenseById() {

        //Given:

        Timestamp date = new Timestamp(System.currentTimeMillis());
        final long id = 1L;
        final Expense expense = new Expense(id, "Cena", 100, (new User(1L, "Maria")), date);
        final ExpenseResponse expected = new ExpenseResponse(expense);

        //When:

        when(expenseService.getExpenseById(id)).thenReturn(Optional.of(expense));

        ExpenseResponse actual = expenseController.getExpenseById(id);

        //Then:

        verify(expenseService, times(1)).getExpenseById(id);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldGetExpensesByUserId() {

        //Given:

        Timestamp date = new Timestamp(System.currentTimeMillis());
        long userId = 1L;

        List<Expense> expenses = Arrays.asList(
                new Expense(1L, "Cena", 100, new User(1L, "Maria"), date),
                new Expense(2L, "Taxi", 10, new User(1L, "Maria"), date)
        );

        List<ExpenseResponse> expected = expenses.stream().map(ExpenseResponse::new).collect(Collectors.toList());

        //When:

        when(expenseService.getExpenseByUserId(userId)).thenReturn(expenses);

        List<ExpenseResponse> actual = expenseController.getExpenseByUserId(userId);

        //Then:

        verify(expenseService, times(1)).getExpenseByUserId(userId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldUpdateExpense() {

        //Given:

        Timestamp date = new Timestamp(System.currentTimeMillis());
        final long id = 1L;
        final ExpenseRequest request =  new ExpenseRequest(id, "Cena", 100, 1L);
        final Expense expected = new Expense(id, "Cena", 100, new User(1L, "Maria"), date);

        //When:

        when(expenseService.updateExpense(request.getUser_id(), request.getId(), request.getDescription(), request.getAmount())).thenReturn(expected);

        ExpenseResponse actual = expenseController.updateExpense(request, id);

        //Then:

        verify(expenseService, times(1)).updateExpense(request.getUser_id(), request.getId(), request.getDescription(), request.getAmount());

        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getUser().getName(), actual.getUserName());
        assertEquals(expected.getAmount(), actual.getAmount());

    }

    @Test
    public void shouldDeleteExpense() {

        //Given:

        final long id = 1L;

        //When:

        when(expenseService.deleteExpense(id)).thenReturn(true);

        String response = expenseController.deleteExpense(id);

        //Then:

        verify(expenseService, times(1)).deleteExpense(id);

        assertEquals("Expense deleted successfully", response);
    }


}
