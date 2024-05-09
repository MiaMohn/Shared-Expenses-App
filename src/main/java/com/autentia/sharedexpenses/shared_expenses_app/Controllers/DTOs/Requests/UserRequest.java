package com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Requests;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class UserRequest {

    //Variables
    private final Long id;
    private String name;


    //Full constructor
    public UserRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
