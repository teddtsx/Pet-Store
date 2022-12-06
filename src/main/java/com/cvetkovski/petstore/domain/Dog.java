package com.cvetkovski.petstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Dog extends Pet {

    private Integer rating;

    @Override
    public Double getPrice() {
        long age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (age < 1) {
            return (double) rating;
        }
        return (double) (age + rating);
    }

    @JsonIgnore
    public String getSuccessfulPurchaseMessage(String ownerName) {
        return String.format("Woof, dog %s has owner %s", name, ownerName);
    }
}
