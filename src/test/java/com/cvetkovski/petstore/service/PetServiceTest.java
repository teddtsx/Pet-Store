package com.cvetkovski.petstore.service;

import com.cvetkovski.petstore.domain.Dog;
import com.cvetkovski.petstore.domain.Pet;
import com.cvetkovski.petstore.dto.response.DogResponse;
import com.cvetkovski.petstore.dto.response.PetResponse;
import com.cvetkovski.petstore.mapper.PetMapper;
import com.cvetkovski.petstore.repository.PetRepository;
import com.cvetkovski.petstore.service.impl.PetServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
class PetServiceTest {

    @Mock
    PetRepository petRepository;

    @Mock
    PetMapper petMapper;

    @InjectMocks
    PetServiceImpl petService;

    @Test
    void testGetAllPets() {
        Dog dog = Dog.builder()
                .id(1L)
                .name("Astro")
                .birthDate(LocalDate.of(2020, 1, 1))
                .description("A cute dog")
                .rating(10)
                .build();

        DogResponse dogResponse = DogResponse.builder()
                .id(1L)
                .name("Astro")
                .birthDate(LocalDate.of(2020, 1, 1))
                .description("A cute dog")
                .rating(10)
                .build();

        when(petRepository.findAll()).thenReturn(List.of(dog));
        when(petMapper.dogToDogResponse(dog)).thenReturn(dogResponse);

        Collection<PetResponse> petResponses = petService.getPets();

        assertNotNull(petResponses);
        assertEquals(1, petResponses.size());
        assertEquals(dogResponse, petResponses.iterator().next());

        verify(petRepository).findAll();
        verify(petMapper).dogToDogResponse(dog);
    }

    @Test
    void testGeneratePets() {
        PetResponse petResponse = PetResponse.builder()
                .id(1L)
                .name("Astro")
                .birthDate(LocalDate.of(2020, 1, 1))
                .description("A cute dog")
                .build();

        when(petRepository.saveAll(anyCollection())).thenAnswer(invocation -> invocation.getArgument(0));
        when(petMapper.petToPetResponse(any())).thenAnswer(invocation -> petResponse);

        Collection<PetResponse> petResponses = petService.generatePets(1);

        assertNotNull(petResponses);
        assertEquals(1, petResponses.size());
        assertEquals(petResponse, petResponses.iterator().next());

        verify(petRepository).saveAll(anyCollection());
        verify(petMapper).petToPetResponse(any());
    }

    @Test
    void testGetPetById() {
        Dog dog = Dog.builder()
                .id(1L)
                .name("Astro")
                .birthDate(LocalDate.of(2020, 1, 1))
                .description("A cute dog")
                .rating(10)
                .build();

        when(petRepository.findById(1L)).thenReturn(Optional.of(dog));

        Pet pet = petService.getPetById(1L);

        assertNotNull(pet);
        assertEquals(dog, pet);

        verify(petRepository).findById(1L);
    }
}