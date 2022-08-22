package com.tinqin.academy.userstore.domain.service;

import com.tinqin.academy.userstore.config.PasswordEncoderConfig;
import com.tinqin.academy.userstore.data.entity.User;
import com.tinqin.academy.userstore.data.repository.UserRepository;
import com.tinqin.academy.userstore.domain.error.UserCreationFailureException;
import com.tinqin.academy.userstore.domain.error.UsernameNotFoundException;
import com.tinqin.academy.userstore.domain.mapper.UserToUserResponseDTOMapper;
import com.tinqin.academy.userstore.view.DTOs.UserDTOCreateRequest;
import com.tinqin.academy.userstore.view.DTOs.UserDTOResponse;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;


@SpringBootTest
@Import(PasswordEncoderConfig.class)
class UserServiceImplTest {

    private UserService toTest;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserToUserResponseDTOMapper userToUserResponseDTOMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void setUp() {
        toTest = new UserServiceImpl(
                mockUserRepository,
                passwordEncoder,
                userToUserResponseDTOMapper
        );
    }

    @Test
    void getUserByUsernameWhenUserExistsShouldReturnUser() {

        // arrange
        User testUserEntity = User.builder()
                .username("nikola00")
                .firstName("Nikola")
                .lastName("Slavchev")
                .age(22)
                .password("topsecret")
                .build();

        when(mockUserRepository.findByUsername(testUserEntity.getUsername())).
                thenReturn(Optional.of(testUserEntity));

        // act
        Either<UsernameNotFoundException, UserDTOResponse> userRes = toTest
                .getByUsername(testUserEntity.getUsername());

        // assert
        assertEquals(
                testUserEntity.getUsername(),
                userRes.get().getUsername());

        assertEquals(
                testUserEntity.getAge(),
                userRes.get().getAge());

        assertEquals(
                testUserEntity.getFirstName(),
                userRes.get().getFirstName());

        assertEquals(
                testUserEntity.getLastName(),
                userRes.get().getLastName());

        assertEquals(
                testUserEntity.getPassword(),
                userRes.get().getPassword());
    }

    @Test
    void getUserByUsernameWhenUserDoesNotExistShouldReturnError() {

        // arrange
        User testUserEntity = User.builder()
                .username("nikola00")
                .firstName("Nikola")
                .lastName("Slavchev")
                .age(22)
                .password("topsecret")
                .build();

        when(mockUserRepository.findByUsername(testUserEntity.getUsername())).
                thenReturn(Optional.empty());

        // act
        Either<UsernameNotFoundException, UserDTOResponse> userRes = toTest
                .getByUsername(testUserEntity.getUsername());

        // assert
        assertEquals(
                userRes.getLeft().getMessage(),
                "User not found with username: " + testUserEntity.getUsername());
    }

    @Test
    void crateUserShouldCreateUser() {
        // arrange
        UserDTOResponse testResponse = UserDTOResponse.builder()
                .username("nikola00")
                .firstName("Nikola")
                .lastName("Slavchev")
                .age(22)
                .password("topsecret")
                .build();

        User testUserEntity = User.builder()
                .username("nikola00")
                .firstName("Nikola")
                .lastName("Slavchev")
                .age(22)
                .password("topsecret")
                .build();

        UserDTOCreateRequest testRequest = UserDTOCreateRequest.builder()
                .firstName("Nikola")
                .lastName("Slavchev")
                .age(22)
                .password("topsecret")
                .username("nikola00")
                .build();


        when(mockUserRepository.findByUsername(testUserEntity.getUsername())).
                thenReturn(Optional.empty());

        when(mockUserRepository.save(any())).
                thenReturn(testUserEntity);

        when(userToUserResponseDTOMapper.map(testUserEntity)).
                thenReturn(testResponse);

        // act
        Either<UserCreationFailureException, UserDTOResponse> actualRes = toTest.createUser(testRequest);

        // assert
        assertEquals(
                testResponse.getUsername(),
                actualRes.get().getUsername());

        assertEquals(
                testResponse.getAge(),
                actualRes.get().getAge());

        assertEquals(
                testResponse.getFirstName(),
                actualRes.get().getFirstName());

        assertEquals(
                testResponse.getLastName(),
                actualRes.get().getLastName());

        assertEquals(testResponse.getPassword(), actualRes.get().getPassword());
    }

    @Test
    void crateUserShouldNotCreateUserAndShouldReturnError() {
        // arrange
        UserDTOResponse testResponse = UserDTOResponse.builder()
                .username("nikola00")
                .firstName("Nikola")
                .lastName("Slavchev")
                .age(22)
                .password("topsecret")
                .build();

        User testUserEntity = User.builder()
                .username("nikola00")
                .firstName("Nikola")
                .lastName("Slavchev")
                .age(22)
                .password("topsecret")
                .build();

        UserDTOCreateRequest testRequest = UserDTOCreateRequest.builder()
                .firstName("Nikola")
                .lastName("Slavchev")
                .age(22)
                .password("topsecret")
                .username("nikola00")
                .build();


        when(mockUserRepository.findByUsername(testUserEntity.getUsername())).
                thenReturn(Optional.of(testUserEntity));

        // act
        Either<UserCreationFailureException, UserDTOResponse> actualRes = toTest.createUser(testRequest);

        // assert
        assertEquals(
                actualRes.getLeft().getMessage(), "User with username '"
                        + testUserEntity.getUsername()
                        + "' already exists.");
    }
}