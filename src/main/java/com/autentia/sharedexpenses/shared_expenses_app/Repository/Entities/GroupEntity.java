package com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities;

import jakarta.persistence.*;

@Entity
@Table(name="FriendGroup")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;
    private String title;
    private String description;

    public GroupEntity(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

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
