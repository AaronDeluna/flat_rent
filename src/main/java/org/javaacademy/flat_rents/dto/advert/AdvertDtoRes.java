package org.javaacademy.flat_rents.dto.advert;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;

import java.math.BigDecimal;

@Data
@Builder
public class AdvertDtoRes {
    private Integer id;
    @JsonProperty("price")
    private BigDecimal pricePerNight;
    @JsonProperty("is_active")
    private Boolean isActive;
    private ApartmentDto apartmentDto;
    private String description;
}
