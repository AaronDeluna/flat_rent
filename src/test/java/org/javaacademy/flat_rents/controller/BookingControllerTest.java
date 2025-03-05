package org.javaacademy.flat_rents.controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.flat_rents.dto.PageDto;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRq;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRq;
import org.javaacademy.flat_rents.dto.client.ClientDto;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.javaacademy.flat_rents.entity.ApartmentType.ONE_BEDROOM;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = "classpath:clean-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BookingControllerTest {
    private static final LocalDate BOOKING_SUCCESS_START = LocalDate.of(2025, 10, 1);
    private static final LocalDate BOOKING_SUCCESS_END = LocalDate.of(2025, 10, 10);
    private static final LocalDate CONFLICT_BOOKING_1_START = LocalDate.of(2025, 10, 5);
    private static final LocalDate CONFLICT_BOOKING_1_END = LocalDate.of(2025, 10, 6);
    private static final LocalDate CONFLICT_BOOKING_2_START = LocalDate.of(2025, 9, 29);
    private static final LocalDate CONFLICT_BOOKING_2_END = LocalDate.of(2025, 10, 2);
    private static final LocalDate CONFLICT_BOOKING_3_START = LocalDate.of(2025, 10, 9);
    private static final LocalDate CONFLICT_BOOKING_3_END = LocalDate.of(2025, 10, 11);

    private static final BigDecimal TOTAL_BOOKING_COST = BigDecimal.valueOf(90);
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/booking")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    @Autowired
    private ClientService clientService;
    @Autowired
    private AdvertService advertService;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private BookingService bookingService;

    @Test
    @DisplayName("Успешное бронирование, при указанном id у клиента")
    public void create() {
        ClientDto clientDto = clientService.save(ClientDto.builder()
                .name("Test name")
                .email("test@eamil.com")
                .build());

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

        BookingDtoRes bookingDtoRes = given(requestSpecification)
                .body(bookingDtoRq)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoRes.class);

        assertEquals(bookingDtoRq.getStartDate(), bookingDtoRes.getStartDate());
        assertEquals(bookingDtoRq.getEndDate(), bookingDtoRes.getEndDate());
        assertEquals(0, bookingDtoRes.getTotalCost().compareTo(TOTAL_BOOKING_COST));
    }

    @Test
    @DisplayName("Успешное бронирование, при незаполненном id у клиента")
    public void shouldCreateBookingWhenClientIdIsNull() {
        ClientDto clientDto = ClientDto.builder()
                .name("Test name")
                .email("test@eamil.com")
                .build();

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

        BookingDtoRes bookingDtoRes = given(requestSpecification)
                .body(bookingDtoRq)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoRes.class);

        assertEquals(bookingDtoRq.getStartDate(), bookingDtoRes.getStartDate());
        assertEquals(bookingDtoRq.getEndDate(), bookingDtoRes.getEndDate());
        assertEquals(clientDto.getEmail(), bookingDtoRes.getClient().getEmail());
        assertEquals(clientDto.getName(), bookingDtoRes.getClient().getName());
        assertEquals(0, bookingDtoRes.getTotalCost().compareTo(TOTAL_BOOKING_COST));
    }

    @Test
    @DisplayName("Ошибка при бронировании с 5 по 6 октября — пересечение с существующей бронью")
    public void shouldFailWhenBookingOverlapsExistingOne5to6() {
        createBooking("test1", BOOKING_SUCCESS_START, BOOKING_SUCCESS_END);

        BookingDtoRq bookingDtoRq = createBooking("test2", CONFLICT_BOOKING_1_START, CONFLICT_BOOKING_1_END);

        given(requestSpecification)
                .body(bookingDtoRq)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName("Ошибка при бронировании с 29 сентября по 2 октября — пересечение с существующей бронью")
    public void shouldFailWhenBookingOverlapsExistingOne29SepTo2Oct() {
        createBooking("test1", BOOKING_SUCCESS_START, BOOKING_SUCCESS_END);

        BookingDtoRq bookingDtoRq = createBooking("test2", CONFLICT_BOOKING_2_START, CONFLICT_BOOKING_2_END);

        given(requestSpecification)
                .body(bookingDtoRq)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName("Ошибка при бронировании с 9 по 11 октября — пересечение с существующей бронью")
    public void shouldFailWhenBookingOverlapsExistingOne9to11() {
        createBooking("test1", BOOKING_SUCCESS_START, BOOKING_SUCCESS_END);

        BookingDtoRq bookingDtoRq = createBooking("test2", CONFLICT_BOOKING_3_START, CONFLICT_BOOKING_3_END);

        given(requestSpecification)
                .body(bookingDtoRq)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName("Успешное получение всех броней по email")
    void getAllByEmail() {
        String clientEmail = "test1";
        BookingDtoRq bookingDtoRq = createBooking(clientEmail, BOOKING_SUCCESS_START, BOOKING_SUCCESS_END);

        PageDto<BookingDtoRes> resPageDto = given(requestSpecification)
                .queryParam("page", 0)
                .get("/%s".formatted(clientEmail))
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(new TypeRef<>() {
                });

        int expectedPageContentSize = 1;
        BookingDtoRes resultBookingDtoRes = resPageDto.getContent().get(0);

        assertEquals(bookingDtoRq.getStartDate(), resultBookingDtoRes.getStartDate());
        assertEquals(bookingDtoRq.getEndDate(), resultBookingDtoRes.getEndDate());
        assertEquals(bookingDtoRq.getClient().getEmail(), resultBookingDtoRes.getClient().getEmail());
        assertEquals(bookingDtoRq.getClient().getName(), resultBookingDtoRes.getClient().getName());
        assertEquals(0, resultBookingDtoRes.getTotalCost().compareTo(TOTAL_BOOKING_COST));
        assertEquals(expectedPageContentSize, resPageDto.getSize());

    }

    private BookingDtoRq createBooking(String clientEmail, LocalDate startDate, LocalDate endDate) {
        ClientDto clientDto = clientService.save(ClientDto.builder()
                .name("Test name")
                .email(clientEmail)
                .build());

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
                .startDate(startDate)
                .endDate(endDate)
                .client(clientDto)
                .advertId(advertId)
                .build();

        bookingService.save(bookingDtoRq);
        return bookingDtoRq;
    }

}
