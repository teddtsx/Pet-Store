package com.cvetkovski.petstore.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakePurchaseRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long petId;
}
