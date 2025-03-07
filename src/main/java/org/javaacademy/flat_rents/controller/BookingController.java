package org.javaacademy.flat_rents.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.ErrorResponse;
import org.javaacademy.flat_rents.dto.PageDto;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRq;
import org.javaacademy.flat_rents.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @Operation(
            summary = "Сохранение бронирования"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешно создано",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookingDtoRes.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Обьявление не найдено",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDtoRes create(@RequestBody BookingDtoRq bookingDtoRq) {
        return bookingService.save(bookingDtoRq);
    }

    @Operation(
            summary = "Найти все резервации по email клиента"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PageDto.class)
                    )
            )
    })
    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public PageDto<BookingDtoRes> getAllByEmail(@PathVariable String email,
                                                @RequestParam Integer page) {
        return bookingService.findAllByEmail(email, page);
    }

}
