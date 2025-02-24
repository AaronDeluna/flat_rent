package org.javaacademy.flat_rents.dto.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientDto {
    private Integer id;
    private String name;
    private String email;
}
