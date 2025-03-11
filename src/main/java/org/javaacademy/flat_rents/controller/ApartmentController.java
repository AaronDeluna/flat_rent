package org.javaacademy.flat_rents.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;
import org.javaacademy.flat_rents.service.ApartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apartment")
@RequiredArgsConstructor
public class ApartmentController {
    private final ApartmentService apartmentService;

    @Operation(
            summary = "Сохранение помещения"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешно создано",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApartmentDto.class)
                    )
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDto create(@RequestBody ApartmentDto apartmentDto) {
        return apartmentService.save(apartmentDto);
    }

}
