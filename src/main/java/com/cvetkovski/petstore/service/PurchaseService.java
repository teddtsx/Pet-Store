package com.cvetkovski.petstore.service;

import com.cvetkovski.petstore.dto.request.MakePurchaseRequest;
import com.cvetkovski.petstore.dto.response.PurchaseResponse;

import java.util.Collection;

public interface PurchaseService {

    PurchaseResponse makePurchase(MakePurchaseRequest makePurchaseRequest);

    Collection<PurchaseResponse> getPurchases();
}
