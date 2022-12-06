package com.cvetkovski.petstore.controller;

import com.cvetkovski.petstore.dto.response.UserResponse;
import com.cvetkovski.petstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/create-users")
    public ResponseEntity<Collection<UserResponse>> createUsers(@RequestParam @Range(min = 1, max = 10) Integer count) {
        Collection<UserResponse> userResponses = userService.generateUsers(count);
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/list-users")
    public ResponseEntity<Collection<UserResponse>> listUsers() {
        Collection<UserResponse> userResponses = userService.getUsers();
        return ResponseEntity.ok(userResponses);
    }
}
