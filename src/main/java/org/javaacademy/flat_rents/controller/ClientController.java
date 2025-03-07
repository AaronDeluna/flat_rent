package org.javaacademy.flat_rents.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.ErrorResponse;
import org.javaacademy.flat_rents.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @Operation(
            summary = "Удаление клиента по id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Клиент не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Integer id) {
        clientService.deleteById(id);
    }

}
