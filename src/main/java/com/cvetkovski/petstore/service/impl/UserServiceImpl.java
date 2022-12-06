package com.cvetkovski.petstore.service.impl;

import com.cvetkovski.petstore.domain.User;
import com.cvetkovski.petstore.dto.response.UserResponse;
import com.cvetkovski.petstore.exception.EntityNotFoundException;
import com.cvetkovski.petstore.mapper.UserMapper;
import com.cvetkovski.petstore.repository.UserRepository;
import com.cvetkovski.petstore.service.UserService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Collection<UserResponse> generateUsers(int count) {
        Faker faker = new Faker();
        List<User> users = IntStream.range(0, count)
                .mapToObj(i -> generateUser(faker))
                .toList();
        Collection<User> savedUsers = userRepository.saveAll(users);
        return userMapper.usersToUserResponses(savedUsers);
    }

    private User generateUser(Faker faker) {
        return User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .budget(faker.number().randomDouble(2, 0, 1000))
                .build();
    }

    @Override
    public Collection<UserResponse> getUsers() {
        Collection<User> users = userRepository.findAll();
        return userMapper.usersToUserResponses(users);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
