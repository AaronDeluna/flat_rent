package org.javaacademy.flat_rents.mapper;

import org.javaacademy.flat_rents.dto.advert.AdvertDtoRes;
import org.javaacademy.flat_rents.dto.advert.CreateAdvertDto;
import org.javaacademy.flat_rents.entity.Advert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ApartmentMapper.class}
)
public interface AdvertMapper {
    @Mapping(target = "bookings", ignore = true)
    Advert toEntity(CreateAdvertDto dto);
    @Mapping(target = "apartmentDto", source = "apartment")
    AdvertDtoRes toResDto(Advert advert);
}
