package org.javaacademy.flat_rents.dto.advert;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateAdvertDto {
    private Integer id;
    @JsonProperty("price")
    private BigDecimal pricePerNight;
    @JsonProperty("is_active")
    private Boolean isActive;
    @JsonProperty("apartment_id")
    private Integer apartmentId;
    private String description;
}
