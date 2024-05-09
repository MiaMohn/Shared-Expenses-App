package com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode
@Data
@Entity
@Table(name="expense")
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double amount;
    private Long user_Id;
    private Timestamp expenseDate;

    public ExpenseEntity(){}

    @Builder
    public ExpenseEntity(Long id, String description, double amount, Long user_Id, Timestamp expenseDate) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.user_Id = user_Id;
        this.expenseDate = expenseDate;
    }

}