package com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Entity
@Table(name="User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    private String name;

    public UserEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
