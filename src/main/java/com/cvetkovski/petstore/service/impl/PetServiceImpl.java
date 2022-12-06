package com.cvetkovski.petstore.service.impl;

import com.cvetkovski.petstore.domain.Dog;
import com.cvetkovski.petstore.domain.Pet;
import com.cvetkovski.petstore.dto.response.PetResponse;
import com.cvetkovski.petstore.exception.EntityNotFoundException;
import com.cvetkovski.petstore.mapper.PetMapper;
import com.cvetkovski.petstore.repository.PetRepository;
import com.cvetkovski.petstore.service.PetService;
import com.cvetkovski.petstore.util.DateTimeUtil;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    @Override
    public Collection<PetResponse> generatePets(int count) {
        Random random = new Random();
        Faker faker = new Faker();
        List<Pet> pets = IntStream.range(0, count)
                .mapToObj(value -> random.nextBoolean())
                .map(isDog -> generatePet(isDog, faker))
                .toList();
        List<Pet> savedPets = petRepository.saveAll(pets);
        return savedPets.stream()
                .map(this::mapPetToPetResponse)
                .toList();
    }

    private Pet generatePet(boolean isDog, Faker faker) {
        if (isDog) {
            return Dog.builder()
                    .name(faker.name().firstName())
                    .description(faker.lorem().sentence())
                    .birthDate(DateTimeUtil.convertDateToLocalDate(faker.date().birthday()))
                    .rating(faker.number().numberBetween(1, 10))
                    .build();
        } else {
            return Pet.builder()
                    .name(faker.name().firstName())
                    .description(faker.lorem().sentence())
                    .birthDate(DateTimeUtil.convertDateToLocalDate(faker.date().birthday()))
                    .build();
        }
    }

    public Collection<PetResponse> getPets() {
        Collection<Pet> pets = petRepository.findAll();
        return pets.stream()
                .map(this::mapPetToPetResponse)
                .toList();
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet with id " + id + " not found"));
    }

    private PetResponse mapPetToPetResponse(Pet pet) {
        if (pet instanceof Dog) {
            return petMapper.dogToDogResponse((Dog) pet);
        } else {
            return petMapper.petToPetResponse(pet);
        }
    }
}
