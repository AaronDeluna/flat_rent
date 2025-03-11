package org.javaacademy.flat_rents.mapper;

import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;
import org.javaacademy.flat_rents.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApartmentMapper {

    Apartment toEntity(ApartmentDto dto);
    ApartmentDto toDto(Apartment apartment);
}
