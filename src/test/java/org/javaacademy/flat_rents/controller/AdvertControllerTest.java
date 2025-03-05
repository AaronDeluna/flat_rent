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
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRes;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRq;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;
import org.javaacademy.flat_rents.repository.AdvertRepository;
import org.javaacademy.flat_rents.service.AdvertService;
import org.javaacademy.flat_rents.service.ApartmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.javaacademy.flat_rents.entity.ApartmentType.ONE_BEDROOM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = "classpath:clean-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AdvertControllerTest {
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/advert")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private AdvertService advertService;

    @Test
    @DisplayName("Успешное создание обьявления")
    void create() {
        int apartmentId = apartmentService.save(ApartmentDto.builder()
                .city("Test city")
                .street("Test street")
                .house("Test house")
                .apartmentType(ONE_BEDROOM)
                .build()).getId();

        AdvertDtoRq advertDtoRq = AdvertDtoRq.builder()
                .price(BigDecimal.TEN)
                .isActive(true)
                .apartmentId(apartmentId)
                .description("Test description")
                .build();

        AdvertDtoRes advertDtoRes = given(requestSpecification)
                .body(advertDtoRq)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(AdvertDtoRes.class);

        assertTrue(advertRepository.existsById(advertDtoRes.getId()));
        assertEquals(0, advertDtoRq.getPrice().compareTo(advertDtoRes.getPrice()));
        assertTrue(advertDtoRes.getIsActive());
        assertNotNull(advertDtoRes.getApartment());
        assertEquals(advertDtoRq.getDescription(), advertDtoRes.getDescription());
    }

    @Test
    @DisplayName("Успешное получение всех обьявлений по названию города")
    void getAllByCityName() {
        String cityName = "City";

        int apartmentId = apartmentService.save(ApartmentDto.builder()
                .city(cityName)
                .street("Test street")
                .house("Test house")
                .apartmentType(ONE_BEDROOM)
                .build()).getId();

        AdvertDtoRq advertDtoRq = AdvertDtoRq.builder()
                .price(BigDecimal.TEN)
                .isActive(true)
                .apartmentId(apartmentId)
                .description("Test description")
                .build();

        advertService.save(advertDtoRq);

        PageDto<AdvertDtoRes> advertPageRes = given(requestSpecification)
                .queryParam("page", 0)
                .get("/%s".formatted(cityName))
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(new TypeRef<>() {
                });

        int expectedPageContentSize = 1;
        AdvertDtoRes resultAdvertDtoRes = advertPageRes.getContent().get(0);

        assertEquals(expectedPageContentSize, advertPageRes.getSize());
        assertEquals(0, advertDtoRq.getPrice().compareTo(resultAdvertDtoRes.getPrice()));
        assertTrue(resultAdvertDtoRes.getIsActive());
        assertEquals(advertDtoRq.getDescription(), resultAdvertDtoRes.getDescription());
    }
}
