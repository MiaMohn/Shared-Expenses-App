package com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="Expense")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    private String description;
    private double amount;
    private Long user_Id;
    private Timestamp expenseDate;

    public ExpenseEntity(Long id, String description, double amount, Long user_Id, Timestamp expenseDate) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.user_Id = user_Id;
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

    public Long getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(Long user_Id) {
        this.user_Id = user_Id;
    }

    public Timestamp getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Timestamp expenseDate) {
        this.expenseDate = expenseDate;
    }
}