package com.cvetkovski.petstore.service;

import com.cvetkovski.petstore.domain.User;
import com.cvetkovski.petstore.dto.response.UserResponse;

import java.util.Collection;

public interface UserService {

    Collection<UserResponse> generateUsers(int count);

    Collection<UserResponse> getUsers();

    User getUserById(Long id);

    void saveUser(User user);
}
