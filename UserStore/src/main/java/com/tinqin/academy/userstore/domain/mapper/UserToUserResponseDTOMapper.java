package com.tinqin.academy.userstore.domain.mapper;

import com.tinqin.academy.userstore.data.entity.User;
import com.tinqin.academy.userstore.view.DTOs.UserDTOResponse;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseDTOMapper {

    public UserDTOResponse map(User user) {
        return UserDTOResponse.builder()
                .password(user.getPassword())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .build();
    }

}
