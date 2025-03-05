package org.javaacademy.flat_rents.controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;
import org.javaacademy.flat_rents.repository.ApartmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.javaacademy.flat_rents.entity.ApartmentType.ONE_BEDROOM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(value = "classpath:clean-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ApartmentControllerTest {
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/apartment")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    @DisplayName("Успешное создание помещения")
    void create() {
        ApartmentDto apartmentDtoExpected = ApartmentDto.builder()
                .city("Test city")
                .street("Test street")
                .house("Test house")
                .apartmentType(ONE_BEDROOM)
                .build();

        ApartmentDto apartmentDtoResult = given(requestSpecification)
                .body(apartmentDtoExpected)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ApartmentDto.class);

        assertTrue(apartmentRepository.existsById(apartmentDtoResult.getId()));
        assertEquals(apartmentDtoExpected.getCity(), apartmentDtoResult.getCity());
        assertEquals(apartmentDtoExpected.getStreet(), apartmentDtoResult.getStreet());
        assertEquals(apartmentDtoExpected.getHouse(), apartmentDtoResult.getHouse());
        assertEquals(apartmentDtoExpected.getApartmentType(), apartmentDtoResult.getApartmentType());
    }
}
