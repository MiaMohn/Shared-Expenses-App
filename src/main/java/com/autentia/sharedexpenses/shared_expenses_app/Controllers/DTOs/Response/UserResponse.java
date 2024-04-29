package com.autentia.sharedexpenses.shared_expenses_app.Controllers.DTOs.Response;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;

public class UserResponse {

    private final Long id;
    private String name;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
