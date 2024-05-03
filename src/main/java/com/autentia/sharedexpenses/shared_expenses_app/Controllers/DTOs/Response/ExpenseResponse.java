package com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@EqualsAndHashCode
public class ExpenseResponse {

    private final Long id;
    private final String description;
    private final double amount;
    private final String user_name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "Europe/Madrid")
    private final String expenseDate;

    public ExpenseResponse(Expense expense) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));

        this.id = expense.getId();
        this.description = expense.getDescription();
        this.amount = expense.getAmount();
        this.user_name = expense.getUser().getName();
        this.expenseDate = dateFormat.format(expense.getExpenseDate());
    }


    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getUserName() {
        return user_name;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

}
