package com.cvetkovski.petstore.service;

import com.cvetkovski.petstore.domain.User;
import com.cvetkovski.petstore.dto.response.PetResponse;
import com.cvetkovski.petstore.dto.response.UserResponse;
import com.cvetkovski.petstore.mapper.UserMapper;
import com.cvetkovski.petstore.repository.UserRepository;
import com.cvetkovski.petstore.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    // TODO - Write tests for UserService

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testGenerateUsers() {
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .firstName("Tom")
                .lastName("Thomson")
                .email("tomthomson@mail.com")
                .budget(1000D)
                .build();

        when(userRepository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userMapper.userToUserResponse(any())).thenAnswer(invocation -> userResponse);

        Collection<UserResponse> userResponses = userService.generateUsers(1);

        assertNotNull(userResponses);
        assertEquals(1, userResponses.size());
        assertEquals(userResponse, userResponses.iterator().next());

        verify(userRepository).saveAll(anyCollection());
        verify(userMapper).usersToUserResponses(any());
    }

    @Test
    void testUserById() {
        User userr = User.builder()
                .id(1L)
                .firstName("Tom")
                .lastName("Thomson")
                .email("tomthomson@mail.com")
                .budget(1000D)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(userr));

        User user = userService.getUserById(1L);

        assertNotNull(user);
        assertEquals(userr, user);

        verify(userRepository).findById(1L);
    }

    @Test
    void testGetAllUsers() {
        User user = User.builder()
                .id(1L)
                .firstName("Tom")
                .lastName("Thomson")
                .email("tomthomson@mail.com")
                .budget(1000D)
                .build();

        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .firstName("Tom")
                .lastName("Thomson")
                .email("tomthomson@mail.com")
                .budget(1000D)
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.userToUserResponse(user)).thenReturn(userResponse);

        Collection<UserResponse> userResponses = userService.getUsers();

        assertNotNull(userResponses);
        assertEquals(1, userResponses.size());
        assertEquals(userResponse, userResponses.iterator().next());

        verify(userRepository).findAll();
        verify(userMapper).userToUserResponse(user);
    }
}