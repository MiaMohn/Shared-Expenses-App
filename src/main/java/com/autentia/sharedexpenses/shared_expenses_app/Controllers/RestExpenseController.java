package com.autentia.sharedexpenses.shared_expenses_app.Controllers;

import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests.ExpenseRequest;
import com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response.ExpenseResponse;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Services.ExpenseService;
import com.autentia.sharedexpenses.shared_expenses_app.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expense")
public class RestExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public RestExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
    }

    //List all users:

    @GetMapping
    public List<ExpenseResponse> getExpenses(){

        List<Expense> expenses = this.expenseService.getExpenses();

        return expenses.stream() //Transformar List<Expense> en List<ExpenseResponse>
                .map(ExpenseResponse::new)
                .collect(Collectors.toList());

    }

    //Create a new expense:

    @PostMapping

    public ResponseEntity<String> createExpense(@RequestBody ExpenseRequest expenseRequest) {

        this.expenseService.createExpense(expenseRequest.getUser_id(), expenseRequest.getDescription(), expenseRequest.getAmount());
        return ResponseEntity.ok("Expense created successfully.");
    }

    //Find an expense by its id:

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable("id") long id){
        Optional<Expense> expense = this.expenseService.getExpenseById(id);
        if (expense.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Expense not found.");
        }
        return ResponseEntity.ok(expense.get());
    }

    //Find an expense by its user id:

    @GetMapping(path = "/user/{user_id}")
    public List<ExpenseResponse> getExpenseByUserId(@PathVariable("user_id") long user_id){

        List<Expense> expenses = this.expenseService.getExpenseByUserId(user_id);

        return expenses.stream() //Transformar List<Expense> en List<ExpenseResponse>
                .map(ExpenseResponse::new)
                .collect(Collectors.toList());

    }

    //Update expense:

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateExpense(@RequestBody ExpenseRequest request, @PathVariable("id") long id){
        Expense expense = this.expenseService.updateExpense(request.getUser_id(), id, request.getDescription(), request.getAmount());
        return ResponseEntity.ok(new ExpenseResponse(expense));
    }

    //Delete expense:

    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable("id") long id){

        boolean deleted = this.expenseService.deleteExpense(id);

        if (deleted){
            return "Expense deleted successfully";
        } else {
            return "Failed to delete expense";
        }
    }
}
