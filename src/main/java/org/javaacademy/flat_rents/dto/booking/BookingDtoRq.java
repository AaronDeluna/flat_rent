package org.javaacademy.flat_rents.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rents.dto.client.ClientDto;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@Schema(description = "DTO для запроса на создание бронирования")
public class BookingDtoRq {

    @Schema(description = "Идентификатор бронирования", example = "1")
    private Integer id;

    @Schema(description = "Информация о клиенте, сделавшем бронирование")
    private ClientDto client;

    @JsonProperty("advert_id")
    @Schema(description = "Идентификатор помещения", example = "10")
    private Integer advertId;

    @JsonProperty("date_start")
    @Schema(description = "Дата начала бронирования", example = "2025-09-01")
    private LocalDate startDate;

    @JsonProperty("date_finish")
    @Schema(description = "Дата окончания бронирования", example = "2025-09-07")
    private LocalDate endDate;

}
