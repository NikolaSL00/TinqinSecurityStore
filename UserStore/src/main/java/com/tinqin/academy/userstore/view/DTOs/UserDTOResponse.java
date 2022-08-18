package com.tinqin.academy.userstore.view.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTOResponse {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Integer age;
}
