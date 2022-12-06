package com.cvetkovski.petstore.mapper;

import com.cvetkovski.petstore.domain.Dog;
import com.cvetkovski.petstore.domain.Pet;
import com.cvetkovski.petstore.dto.response.DogResponse;
import com.cvetkovski.petstore.dto.response.PetResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {

    @SuppressWarnings("unused")
    PetResponse petToPetResponse(Pet pet);

    @SuppressWarnings("unused")
    DogResponse dogToDogResponse(Dog dog);
}
