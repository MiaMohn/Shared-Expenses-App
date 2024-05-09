package com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@Entity
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public UserEntity(){}

    @Builder
    public UserEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
