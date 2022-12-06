package com.cvetkovski.petstore.controller;

import com.cvetkovski.petstore.dto.request.MakePurchaseRequest;
import com.cvetkovski.petstore.dto.response.PurchaseResponse;
import com.cvetkovski.petstore.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/make-purchase")
    public ResponseEntity<PurchaseResponse> makePurchase(@RequestBody @Valid MakePurchaseRequest makePurchaseRequest) {
        PurchaseResponse purchaseResponse = purchaseService.makePurchase(makePurchaseRequest);
        return ResponseEntity.ok(purchaseResponse);
    }


    @GetMapping("/history-log")
    public ResponseEntity<Collection<PurchaseResponse>> getPurchaseHistory() {
        Collection<PurchaseResponse> purchaseResponses = purchaseService.getPurchases();
        return ResponseEntity.ok(purchaseResponses);
    }
}
