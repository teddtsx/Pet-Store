package com.cvetkovski.petstore.mapper;

import com.cvetkovski.petstore.domain.Purchase;
import com.cvetkovski.petstore.dto.response.PurchaseResponse;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    PurchaseResponse purchaseToPurchaseResponse(Purchase purchase);

    Collection<PurchaseResponse> purchasesToPurchaseResponses(Collection<Purchase> purchases);
}
