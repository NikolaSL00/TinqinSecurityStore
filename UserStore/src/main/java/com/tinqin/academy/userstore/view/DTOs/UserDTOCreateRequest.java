package com.tinqin.academy.userstore.view.DTOs;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class UserDTOCreateRequest {
    @NotEmpty
    @NotNull
    private final String username;
    @NotEmpty
    @NotNull
    @Size(min = 4, message = "password should have at least 2 characters")
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Integer age;

    public UserDTOCreateRequest(String username, String password, String firstName, String lastName, Integer age) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
