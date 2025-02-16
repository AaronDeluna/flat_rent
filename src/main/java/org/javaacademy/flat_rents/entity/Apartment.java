package org.javaacademy.flat_rents.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apartment {

    @Id
    private Integer id;

    private String city;

    private String street;

    private String house;

    @Enumerated(value = EnumType.STRING)
    private RoomsCount roomsCount;

}
