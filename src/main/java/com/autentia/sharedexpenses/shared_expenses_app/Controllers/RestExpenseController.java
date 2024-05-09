package com.autentia.sharedexpenses.shared_expenses_app.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.ExpenseRequest;
import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response.ExpenseResponse;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Services.Exceptions.ExpenseNotFoundException;
import com.autentia.sharedexpenses.shared_expenses_app.Services.ExpenseService;
import com.autentia.sharedexpenses.shared_expenses_app.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expense")
public class RestExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public RestExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
    }

    //List all expenses:

    @GetMapping
    public List<ExpenseResponse> getExpenses(){

        List<Expense> expenses = this.expenseService.getExpenses();

        return expenses.stream() //Transformar List<Expense> en List<ExpenseResponse>
                .map(ExpenseResponse::new)
                .collect(Collectors.toList());

    }

    //Create a new expense:

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpense(@RequestBody ExpenseRequest expenseRequest) {
        this.expenseService.createExpense(expenseRequest.getUser_id(), expenseRequest.getDescription(), expenseRequest.getAmount());
    }

    //Find an expense by its id:

    @GetMapping(path = "/{id}")
    public ExpenseResponse getExpenseById(@PathVariable("id") long id){
        return expenseService.getExpenseById(id).map(ExpenseResponse::new).orElseThrow(ExpenseNotFoundException::new);
    }

    //Find an expense by its user id:

    @GetMapping(path = "/user/{user_id}")
    public List<ExpenseResponse> getExpenseByUserId(@PathVariable("user_id") long user_id){

        List<Expense> expenses = this.expenseService.getExpenseByUserId(user_id);
        return expenses.stream().map(ExpenseResponse::new).collect(Collectors.toList());

    }

    //Update expense:

    @PutMapping(path = "/{id}")
    public ExpenseResponse updateExpense(@RequestBody ExpenseRequest request, @PathVariable("id") long id){

        Expense expense = expenseService.updateExpense(request.getUser_id(), id, request.getDescription(), request.getAmount());

        return new ExpenseResponse(expense);
    }

    //Delete expense:

    @DeleteMapping(path = "/{id}")
    public String deleteExpense(@PathVariable("id") long id){

        boolean deleted = this.expenseService.deleteExpense(id);

        if (deleted){
            return "Expense deleted successfully";
        } else {
            return "Failed to delete expense";
        }
    }
}
