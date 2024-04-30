package com.autentia.sharedexpenses.shared_expenses_app.Services.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExpenseNotFoundException extends RuntimeException{

    public ExpenseNotFoundException() {
        super("Expense not found");
    }

}
