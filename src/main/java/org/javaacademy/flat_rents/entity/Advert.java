package org.javaacademy.flat_rents.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Advert {

    @Id
    private Integer id;

    private BigDecimal pricePerNight;

    private Boolean isActive;

    private Integer apartmentId;

    private String description;

}
