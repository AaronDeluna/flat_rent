package org.javaacademy.flat_rents.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Schema(description = "DTO для представления клиента")
public class ClientDto {

    @Schema(description = "Идентификатор клиента", example = "1")
    private Integer id;

    @Schema(description = "Имя клиента", example = "Иван Иванов")
    private String name;

    @Schema(description = "Электронная почта клиента", example = "ivanov@mail.com")
    private String email;
}
