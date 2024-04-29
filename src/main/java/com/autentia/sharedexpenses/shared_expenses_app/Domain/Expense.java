package com.autentia.sharedexpenses.shared_expenses_app.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Expense {

    //Variables
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
        //LocalDateTime now = this.expenseDate.toLocalDateTime();
        //ZonedDateTime zonedDateTime = now.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Europe/Madrid"));
        //return Timestamp.from(zonedDateTime.toInstant());
        return this.expenseDate;
    }

    public void setExpenseDate(Timestamp expenseDate) {
        this.expenseDate = expenseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(amount, that.amount) && Objects.equals(user, that.user) && Objects.equals(expenseDate, that.expenseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, amount, user, expenseDate);
    }
}
