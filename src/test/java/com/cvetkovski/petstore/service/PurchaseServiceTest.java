package com.cvetkovski.petstore.service;

import com.cvetkovski.petstore.domain.Pet;
import com.cvetkovski.petstore.domain.Purchase;
import com.cvetkovski.petstore.domain.User;
import com.cvetkovski.petstore.dto.request.MakePurchaseRequest;
import com.cvetkovski.petstore.dto.response.PetResponse;
import com.cvetkovski.petstore.dto.response.PurchaseResponse;
import com.cvetkovski.petstore.dto.response.UserResponse;
import com.cvetkovski.petstore.mapper.PurchaseMapper;
import com.cvetkovski.petstore.repository.PurchaseRepository;
import com.cvetkovski.petstore.service.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    PetService petService;

    @Mock
    UserService userService;

    @Mock
    PurchaseMapper purchaseMapper;

    @InjectMocks
    PurchaseServiceImpl purchaseService;

    @Test
    void testGetPurchases() {
        User user = User.builder()
                .id(1L)
                .build();
        Pet pet = Pet.builder()
                .id(1L)
                .build();
        Purchase purchase = Purchase.builder()
                .id(1L)
                .pet(pet)
                .user(user)
                .build();
        PetResponse petResponse = PetResponse.builder()
                .id(1L)
                .build();
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .build();
        PurchaseResponse purchaseResponse = PurchaseResponse.builder()
                .id(1L)
                .pet(petResponse)
                .user(userResponse)
                .build();

        when(purchaseRepository.findAll()).thenReturn(List.of(purchase));
        when(purchaseMapper.purchasesToPurchaseResponses(List.of(purchase))).thenReturn(List.of(purchaseResponse));

        Collection<PurchaseResponse> purchaseResponses = purchaseService.getPurchases();

        assertNotNull(purchaseResponses);
        assertEquals(1, purchaseResponses.size());

        verify(purchaseRepository).findAll();
        verify(purchaseMapper).purchasesToPurchaseResponses(List.of(purchase));
    }

    @Test
    void testMakePurchase() {
        User user = User.builder()
                .id(1L)
                .budget(1000D)
                .pets(new HashSet<>())
                .build();
        Pet pet = Pet.builder()
                .id(1L)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        User userToBeSaved = User.builder()
                .id(1L)
                .budget(978D)
                .pets(Set.of(pet))
                .build();
        Purchase purchase = Purchase.builder()
                .pet(pet)
                .user(user)
                .isSuccessful(true)
                .build();
        PetResponse petResponse = PetResponse.builder()
                .id(1L)
                .birthDate(LocalDate.of(2020, 1, 1))
                .build();
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .budget(1000D)
                .build();
        PurchaseResponse expectedPurchaseResponse = PurchaseResponse.builder()
                .id(1L)
                .pet(petResponse)
                .user(userResponse)
                .build();

        when(userService.getUserById(user.getId())).thenReturn(user);
        when(petService.getPetById(pet.getId())).thenReturn(pet);
        when(purchaseRepository.save(purchase)).thenReturn(purchase);
        when(purchaseMapper.purchaseToPurchaseResponse(purchase)).thenReturn(expectedPurchaseResponse);

        PurchaseResponse purchaseResponse = purchaseService.makePurchase(MakePurchaseRequest.builder()
                .petId(1L)
                .userId(1L)
                .build());

        assertNotNull(purchaseResponse);
        assertEquals(1L, purchaseResponse.getId());

        verify(userService).getUserById(user.getId());
        verify(petService).getPetById(pet.getId());
        verify(userService).saveUser(userToBeSaved);
        verify(purchaseRepository).save(purchase);
        verify(purchaseMapper).purchaseToPurchaseResponse(purchase);
    }
}