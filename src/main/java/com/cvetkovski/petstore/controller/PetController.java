package com.cvetkovski.petstore.controller;

import com.cvetkovski.petstore.dto.response.PetResponse;
import com.cvetkovski.petstore.service.PetService;
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
@RequestMapping("/api/pets")
@Validated
public class PetController {

    private final PetService petService;

    @PostMapping("/create-pets")
    public ResponseEntity<Collection<PetResponse>> createPets(@RequestParam @Range(min = 1, max = 20) Integer count) {
        Collection<PetResponse> petResponses = petService.generatePets(count);
        return ResponseEntity.ok(petResponses);
    }

    @GetMapping("/list-pets")
    public ResponseEntity<Collection<PetResponse>> listPets() {
        Collection<PetResponse> petResponses = petService.getPets();
        return ResponseEntity.ok(petResponses);
    }
}
