package com.autentia.sharedexpenses.shared_expenses_app.TestsIntegrados.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Services.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ExpenseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ExpenseService expenseService;

    private List<Expense> expenses;
    private Timestamp date = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    void setUp() {

        expenses = Arrays.asList(
                new Expense(1L, "Compra", 54.67, new User(1L, "Maria"), date),
                new Expense(2L, "Taxi", 10, new User(1L, "Maria"), date),
                new Expense(3L, "Cena", 100, new User(2L, "Belen"), date)
        );

        when(expenseService.getExpenses()).thenReturn(expenses);
        expenses.forEach(expense -> when(expenseService.getExpenseById(expense.getId())).thenReturn(Optional.of(expense)));
        expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getUser().getId()))
                .forEach((userId, userExpenses) ->
                        when(expenseService.getExpenseByUserId(userId)).thenReturn(userExpenses)
                );
        when(expenseService.updateExpense(1L, 1L, "Museo", 25.50))
                .thenReturn(new Expense(1L, "Museo", 25.50, new User(1L, "Maria"), date));
        when(expenseService.deleteExpense(1L)).thenReturn(true);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .defaultRequest(get("/").contentType(MediaType.APPLICATION_JSON))
                .build();

    }

    @Test
    void shouldGetExpenses() throws Exception {
        mockMvc.perform(get("/expense"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].description").value("Compra"))
                .andExpect(jsonPath("$[1].description").value("Taxi"))
                .andExpect(jsonPath("$[2].description").value("Cena"))
                .andExpect(jsonPath("$[0].amount").value(54.67))
                .andExpect(jsonPath("$[1].amount").value(10))
                .andExpect(jsonPath("$[2].amount").value(100))
                .andExpect(jsonPath("$[0].userName").value("Maria"))
                .andExpect(jsonPath("$[1].userName").value("Maria"))
                .andExpect(jsonPath("$[2].userName").value("Belen"));
    }

    @Test
    void shouldCreateExpense() throws Exception {

        String newExpenseJson = "{\"description\": \"Museo\", \"amount\": 25.50, \"user_id\": 1}";

        mockMvc.perform(post("/expense")
                .content(newExpenseJson))
                .andExpect(status().isCreated());

        verify(expenseService).createExpense(any(Long.class), any(String.class), any(Double.class));
    }

    @Test
    void shouldGetExpenseById() throws Exception {

        for (Expense expense : expenses) {
            mockMvc.perform(get("/expense/{id}", expense.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(expense.getId()))
                    .andExpect(jsonPath("$.description").value(expense.getDescription()))
                    .andExpect(jsonPath("$.amount").value(expense.getAmount()))
                    .andExpect(jsonPath("$.userName").value(expense.getUser().getName()));
        }
    }

    @Test
    void shouldGetExpenseByUserId() throws Exception {

        mockMvc.perform(get("/expense/user/{user_id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Compra"))
                .andExpect(jsonPath("$[0].amount").value(54.67))
                .andExpect(jsonPath("$[0].userName").value("Maria"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("Taxi"))
                .andExpect(jsonPath("$[1].amount").value(10))
                .andExpect(jsonPath("$[1].userName").value("Maria"));


    }

    @Test
    void shouldUpdateExpense() throws Exception {

        String updatedExpenseJson = "{\"description\": \"Museo\", \"amount\": 25.50, \"user_id\": 1}";

        mockMvc.perform(put("/expense/{id}", 1L)
                .content(updatedExpenseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Museo"))
                .andExpect(jsonPath("$.amount").value(25.50))
                .andExpect(jsonPath("$.userName").value("Maria"));
    }

    @Test
    void shouldDeleteExpense() throws Exception {
        mockMvc.perform(delete("/expense/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Expense deleted successfully"));
    }

}
