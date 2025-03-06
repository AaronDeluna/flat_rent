package org.javaacademy.flat_rents.controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRq;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRq;
import org.javaacademy.flat_rents.dto.client.ClientDto;
import org.javaacademy.flat_rents.repository.ClientRepository;
import org.javaacademy.flat_rents.service.AdvertService;
import org.javaacademy.flat_rents.service.ApartmentService;
import org.javaacademy.flat_rents.service.BookingService;
import org.javaacademy.flat_rents.service.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.javaacademy.flat_rents.entity.ApartmentType.ONE_BEDROOM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(value = "classpath:clean-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ClientControllerTest {
    private static final LocalDate BOOKING_SUCCESS_START = LocalDate.of(2025, 10, 1);
    private static final LocalDate BOOKING_SUCCESS_END = LocalDate.of(2025, 10, 10);
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/client")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    @Autowired
    private ClientService clientService;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private AdvertService advertService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Успешное удаление клиента по id")
    public void deleteById() {
        ClientDto clientDto = clientService.save(ClientDto.builder()
                .name("Test name")
                .email("test@email")
                .build());

        createBooking(clientDto);

        List<BookingDtoRes> bookingsBeforeDelete = bookingService.findAllByEmail(clientDto.getEmail(), 0)
                .getContent();

        assertEquals(1, bookingsBeforeDelete.size());

        given(requestSpecification)
                .delete("/%s".formatted(clientDto.getId()))
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.OK.value());

        List<BookingDtoRes> bookingsAfterDelete = bookingService.findAllByEmail(clientDto.getEmail(), 0)
                .getContent();
        assertEquals(0, bookingsAfterDelete.size());
        assertFalse(clientRepository.existsById(clientDto.getId()));
    }

    @Test
    @DisplayName("Ошибка при удалении несуществующего клиента по id")
    public void deleteNonExistentClientShouldReturnNotFound() {
        given(requestSpecification)
                .delete("/0")
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void createBooking(ClientDto clientDto) {
        int apartmentId = apartmentService.save(ApartmentDto.builder()
                .city("Test name")
                .street("Test street")
                .house("Test house")
                .apartmentType(ONE_BEDROOM)
                .build()).getId();

        int advertId = advertService.save(AdvertDtoRq.builder()
                .price(BigDecimal.TEN)
                .isActive(true)
                .apartmentId(apartmentId)
                .description("Test description")
                .build()).getId();

        BookingDtoRq bookingDtoRq = BookingDtoRq.builder()
                .startDate(BOOKING_SUCCESS_START)
                .endDate(BOOKING_SUCCESS_END)
                .client(clientDto)
                .advertId(advertId)
                .build();

        bookingService.save(bookingDtoRq);
    }

}
