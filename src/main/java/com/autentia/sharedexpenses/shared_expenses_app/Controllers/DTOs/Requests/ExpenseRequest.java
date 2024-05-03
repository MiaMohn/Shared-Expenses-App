package com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ExpenseRequest {

    private final Long id;
    private String description;
    private double amount;
    private Long user_id;

    public ExpenseRequest(Long id, String description, double amount, Long user_id) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.user_id = user_id;
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

}
