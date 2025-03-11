package org.javaacademy.flat_rents.dto.apartment;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rents.entity.ApartmentType;

@Builder
@Getter
@Setter
@Schema(description = "DTO для представления квартиры")
public class ApartmentDto {

    @Schema(description = "Идентификатор квартиры", example = "1")
    private Integer id;

    @Schema(description = "Город, в котором находится квартира", example = "Москва")
    private String city;

    @Schema(description = "Улица, на которой находится квартира", example = "Арбат")
    private String street;

    @Schema(description = "Номер дома", example = "12")
    private String house;

    @JsonProperty("apartment_type")
    @Schema(description = "Тип квартиры", example = "STUDIO")
    private ApartmentType apartmentType;

}
