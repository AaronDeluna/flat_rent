package org.javaacademy.flat_rents.mapper;

import org.javaacademy.flat_rents.dto.advert.AdvertDtoRes;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRq;
import org.javaacademy.flat_rents.entity.Advert;
import org.javaacademy.flat_rents.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdvertMapper {

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartment", source = "apartment")
    Advert toEntity(AdvertDtoRq dtoRq, Apartment apartment);

    AdvertDtoRes toDtoRes(Advert advert);

}
