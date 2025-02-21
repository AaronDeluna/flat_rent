package org.javaacademy.flat_rents.dto.booking;

import lombok.Builder;
import lombok.Data;
import org.javaacademy.flat_rents.dto.advert.CreateAdvertDto;
import org.javaacademy.flat_rents.dto.client.ClientDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class BookingDtoRes {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private ClientDto clientDto;
    private CreateAdvertDto createAdvertDto;
    private BigDecimal totalCost;
}
