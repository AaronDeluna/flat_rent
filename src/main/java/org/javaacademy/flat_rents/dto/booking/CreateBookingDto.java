package org.javaacademy.flat_rents.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CreateBookingDto {
    private Integer id;
    @JsonProperty("client_id")
    private Integer clientId;
    @JsonProperty("advert_id")
    private Integer advertId;
    @JsonProperty("date_start")
    private LocalDate startDate;
    @JsonProperty("date_finish")
    private LocalDate endDate;
    @JsonProperty("result_price")
    private BigDecimal totalCost;
}
