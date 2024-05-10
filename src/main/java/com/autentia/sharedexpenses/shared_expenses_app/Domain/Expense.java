package com.autentia.sharedexpenses.shared_expenses_app.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode
public class Expense {

    private final Long id;
    private String description;
    private double amount;
    private User user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.S", timezone = "Europe/Madrid")
    private Timestamp expenseDate;


    public Expense(Long id, String description, double amount, User user, Timestamp expenseDate) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.user = user;
        this.expenseDate = expenseDate;
    }


    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getExpenseDate() {
        return this.expenseDate;
    }

    public void setExpenseDate(Timestamp expenseDate) {
        this.expenseDate = expenseDate;
    }
}
