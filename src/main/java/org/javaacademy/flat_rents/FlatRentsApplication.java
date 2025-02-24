package org.javaacademy.flat_rents;

import org.javaacademy.flat_rents.dto.advert.AdvertDtoRes;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRq;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRq;
import org.javaacademy.flat_rents.dto.client.ClientDto;
import org.javaacademy.flat_rents.entity.ApartmentType;
import org.javaacademy.flat_rents.service.AdvertService;
import org.javaacademy.flat_rents.service.ApartmentService;
import org.javaacademy.flat_rents.service.BookingService;
import org.javaacademy.flat_rents.service.ClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class FlatRentsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlatRentsApplication.class, args);
        ApartmentService apartmentService = context.getBean(ApartmentService.class);
        ClientService clientService = context.getBean(ClientService.class);
        AdvertService advertService = context.getBean(AdvertService.class);
        BookingService bookingService = context.getBean(BookingService.class);


        ApartmentDto apartmentDto = apartmentService.save(
                ApartmentDto.builder()
                        .city("city")
                        .street("street")
                        .house("house")
                        .apartmentType(ApartmentType.ONE_BEDROOM)
                        .build()
        );

        ClientDto clientDto = clientService.save(
                ClientDto.builder()
                        .name("Client")
                        .email("email")
                        .build()
        );

        AdvertDtoRes advertDtoRes = advertService.save(
                AdvertDtoRq.builder()
                        .price(BigDecimal.TEN)
                        .isActive(true)
                        .apartmentId(apartmentDto.getId())
                        .description("description")
                        .build()
        );

        BookingDtoRes bookingDtoRes = bookingService.save(
                BookingDtoRq.builder()
                        .clientId(clientDto.getId())
                        .advertId(advertDtoRes.getId())
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(2))
                        .build()
        );

        System.out.println("ddd");
    }

}
