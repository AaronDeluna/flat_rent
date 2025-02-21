package org.javaacademy.flat_rents.dto.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private Integer id;
    private String name;
    private String email;
}
