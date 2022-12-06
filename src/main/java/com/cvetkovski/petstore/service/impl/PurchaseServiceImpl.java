package com.cvetkovski.petstore.service.impl;

import com.cvetkovski.petstore.domain.Pet;
import com.cvetkovski.petstore.domain.Purchase;
import com.cvetkovski.petstore.domain.User;
import com.cvetkovski.petstore.dto.request.MakePurchaseRequest;
import com.cvetkovski.petstore.dto.response.PurchaseResponse;
import com.cvetkovski.petstore.mapper.PurchaseMapper;
import com.cvetkovski.petstore.repository.PurchaseRepository;
import com.cvetkovski.petstore.service.PetService;
import com.cvetkovski.petstore.service.PurchaseService;
import com.cvetkovski.petstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserService userService;
    private final PetService petService;
    private final PurchaseMapper purchaseMapper;

    @Override
    public PurchaseResponse makePurchase(MakePurchaseRequest makePurchaseRequest) {
        User user = userService.getUserById(makePurchaseRequest.getUserId());
        Pet pet = petService.getPetById(makePurchaseRequest.getPetId());
        boolean hasOwner = pet.getOwner() != null;
        boolean hasEnoughBudget = user.getBudget() >= pet.getPrice();

        if (hasOwner || !hasEnoughBudget) {
            log.info("Pet {} is already owned by someone or user {} doesn't have enough budget to buy it", pet, user);
            return createPurchase(user, pet, false);
        }

        user.setBudget(user.getBudget() - pet.getPrice());
        user.addPet(pet);
        userService.saveUser(user);
        log.info(pet.getSuccessfulPurchaseMessage(user.getFullName()));
        return createPurchase(user, pet, true);
    }

    private PurchaseResponse createPurchase(User user, Pet pet, boolean isSuccessful) {
        Purchase purchase = Purchase.builder()
                .pet(pet)
                .user(user)
                .isSuccessful(isSuccessful)
                .build();
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return purchaseMapper.purchaseToPurchaseResponse(savedPurchase);
    }

    @Override
    public Collection<PurchaseResponse> getPurchases() {
        Collection<Purchase> purchases = purchaseRepository.findAll();
        return purchaseMapper.purchasesToPurchaseResponses(purchases);
    }
}
