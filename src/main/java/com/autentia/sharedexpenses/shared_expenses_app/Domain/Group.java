package com.autentia.sharedexpenses.shared_expenses_app.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Group {

    //Variables
    @Id
    private final int id;
    private String title;
    private String description;

    //Constructor
    public Group(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    //Getters & Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
