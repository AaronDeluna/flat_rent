package org.javaacademy.flat_rents.dto.apartment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.javaacademy.flat_rents.entity.ApartmentType;

@Builder
@Data
public class ApartmentDto {
    private Integer id;
    private String city;
    private String street;
    private String house;
    @JsonProperty("apartment_type")
    private ApartmentType apartmentType;
}
