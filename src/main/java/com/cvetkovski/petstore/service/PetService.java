package com.cvetkovski.petstore.service;

import com.cvetkovski.petstore.domain.Pet;
import com.cvetkovski.petstore.dto.response.PetResponse;

import java.util.Collection;

public interface PetService {

    Collection<PetResponse> generatePets(int count);

    Collection<PetResponse> getPets();

    Pet getPetById(Long id);
}
