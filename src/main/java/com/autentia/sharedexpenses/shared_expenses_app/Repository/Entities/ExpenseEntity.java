package com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
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
    private User user;
    private Timestamp expenseDate;
}
