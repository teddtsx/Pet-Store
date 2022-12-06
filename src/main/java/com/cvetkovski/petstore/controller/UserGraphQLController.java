package com.cvetkovski.petstore.controller;

import com.cvetkovski.petstore.dto.response.UserResponse;
import com.cvetkovski.petstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserGraphQLController {

    private final UserService userService;

    @QueryMapping
    Iterable<UserResponse> users() {
        return userService.getUsers();
    }
}
