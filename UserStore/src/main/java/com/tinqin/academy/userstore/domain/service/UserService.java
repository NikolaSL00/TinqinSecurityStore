package com.tinqin.academy.userstore.domain.service;

import com.tinqin.academy.userstore.domain.error.UserCreationFailureException;
import com.tinqin.academy.userstore.domain.error.UsernameNotFoundException;
import com.tinqin.academy.userstore.view.DTOs.UserDTOCreateRequest;
import com.tinqin.academy.userstore.view.DTOs.UserDTOFindRequest;
import com.tinqin.academy.userstore.view.DTOs.UserDTOResponse;
import io.vavr.control.Either;

public interface UserService {
    Either<UsernameNotFoundException, UserDTOResponse> getByUsername(String username);

    Either<UserCreationFailureException, UserDTOResponse> createUser(UserDTOCreateRequest userDTOCreateRequest);

}
