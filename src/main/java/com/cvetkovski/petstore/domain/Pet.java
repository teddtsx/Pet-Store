package com.cvetkovski.petstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    protected String description;

    protected LocalDate birthDate;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    protected User owner;

    public Double getPrice() {
        long age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (age < 1) {
            return 1D;
        }
        return (double) age;
    }

    public String getSuccessfulPurchaseMessage(String ownerName) {
        return String.format("Meow, cat %s has owner %s", name, ownerName);
    }
}
