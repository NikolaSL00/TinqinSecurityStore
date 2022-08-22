package com.tinqin.academy.userstore.domain.service;

import com.tinqin.academy.userstore.data.entity.User;
import com.tinqin.academy.userstore.data.repository.UserRepository;
import com.tinqin.academy.userstore.domain.error.UserCreationFailureException;
import com.tinqin.academy.userstore.domain.error.UsernameNotFoundException;
import com.tinqin.academy.userstore.domain.mapper.UserToUserResponseDTOMapper;
import com.tinqin.academy.userstore.view.DTOs.UserDTOCreateRequest;
import com.tinqin.academy.userstore.view.DTOs.UserDTOFindRequest;
import com.tinqin.academy.userstore.view.DTOs.UserDTOResponse;
import io.vavr.control.Either;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserToUserResponseDTOMapper userToUserResponseDTOMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserToUserResponseDTOMapper userToUserResponseDTOMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userToUserResponseDTOMapper = userToUserResponseDTOMapper;
    }

    @Override
    public Either<UsernameNotFoundException, UserDTOResponse> getByUsername(String username) {
        Optional<User> optUser = userRepository.findByUsername(username);

        if (optUser.isEmpty()) {
            return Either.left(new UsernameNotFoundException(
                    "User not found with username: " +
                            username)
            );
        }

        return Either.right(UserDTOResponse.builder()
                .username(optUser.get().getUsername())
                .password(optUser.get().getPassword())
                .firstName(optUser.get().getFirstName())
                .lastName(optUser.get().getLastName())
                .age(optUser.get().getAge())
                .build());
    }

    @Override
    public Either<UserCreationFailureException, UserDTOResponse> createUser(UserDTOCreateRequest userDTOCreateRequest) {
        Optional<User> optUser = userRepository.findByUsername(userDTOCreateRequest.getUsername());

        if (optUser.isPresent()) {
            return Either.left(new UserCreationFailureException("User with username '"
                    + userDTOCreateRequest.getUsername()
                    + "' already exists."));
        }

        User savedUser = userRepository.save(User.builder()
                .username(userDTOCreateRequest.getUsername())
                .password(passwordEncoder.encode(userDTOCreateRequest.getPassword()))
                .firstName(userDTOCreateRequest.getFirstName())
                .lastName(userDTOCreateRequest.getLastName())
                .age(userDTOCreateRequest.getAge())
                .build());

        return Either.right(userToUserResponseDTOMapper.map(savedUser));
    }
}
