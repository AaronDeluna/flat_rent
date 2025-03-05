package org.javaacademy.flat_rents.dto.advert;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@Schema(description = "DTO для представления объявления")
public class AdvertDtoRes {

    @Schema(description = "Идентификатор объявления", example = "1")
    private Integer id;

    @Schema(description = "Цена объявления", example = "2500.50")
    private BigDecimal price;

    @JsonProperty("is_active")
    @Schema(description = "Флаг, указывающий активность объявления", example = "true")
    private Boolean isActive;

    @Schema(description = "Информация о помещении, к которому относится объявление")
    private ApartmentDto apartment;

    @Schema(description = "Описание объявления")
    private String description;

}
