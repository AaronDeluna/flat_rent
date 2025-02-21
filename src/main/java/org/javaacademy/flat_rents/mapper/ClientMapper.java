package org.javaacademy.flat_rents.mapper;

import org.javaacademy.flat_rents.dto.client.ClientDto;
import org.javaacademy.flat_rents.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {
    @Mapping(target = "bookings", ignore = true)
    Client toEntity(ClientDto dto);
}
