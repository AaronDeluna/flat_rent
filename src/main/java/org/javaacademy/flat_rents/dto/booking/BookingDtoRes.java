package org.javaacademy.flat_rents.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRes;
import org.javaacademy.flat_rents.dto.client.ClientDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
public class BookingDtoRes {
    private Integer id;
    private ClientDto client;
    private AdvertDtoRes advert;
    @JsonProperty("date_start")
    private LocalDate startDate;
    @JsonProperty("date_finish")
    private LocalDate endDate;
    @JsonProperty("result_price")
    private BigDecimal totalCost;

}
