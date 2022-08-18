package com.tinqin.academy.userstore.view.controller;

import com.tinqin.academy.userstore.domain.error.UserCreationFailureException;
import com.tinqin.academy.userstore.domain.error.UsernameNotFoundException;
import com.tinqin.academy.userstore.domain.service.UserService;
import com.tinqin.academy.userstore.view.DTOs.UserDTOCreateRequest;
import com.tinqin.academy.userstore.view.DTOs.UserDTOResponse;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<UserDTOResponse> findByUsername(@RequestParam(value = "username") String username) {
        Either<UsernameNotFoundException, UserDTOResponse> result = userService.getByUsername(username);

        if (result.isRight()) {
            return ResponseEntity
                    .ok()
                    .body(result.get());
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PostMapping("/")
    public ResponseEntity<UserDTOResponse> createUser(@RequestBody UserDTOCreateRequest userDTOCreateRequest){

        Either<UserCreationFailureException, UserDTOResponse> result = userService.createUser(userDTOCreateRequest);
        if (result.isRight()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(result.get());
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build();
    }

}
