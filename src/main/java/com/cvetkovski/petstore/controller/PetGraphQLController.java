package com.cvetkovski.petstore.controller;

import com.cvetkovski.petstore.dto.response.PetResponse;
import com.cvetkovski.petstore.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PetGraphQLController {

    private final PetService petService;

    @QueryMapping
    Iterable<PetResponse> pets() {
        return petService.getPets();
    }
}
