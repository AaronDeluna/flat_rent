package org.javaacademy.flat_rents.mapper;

import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRq;
import org.javaacademy.flat_rents.entity.Advert;
import org.javaacademy.flat_rents.entity.Booking;
import org.javaacademy.flat_rents.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClientMapper.class, AdvertMapper.class}
)
public interface BookingMapper {
    @Mapping(target = "totalCost", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "client")
    @Mapping(target = "advert", source = "advert")
    Booking toEntity(BookingDtoRq dtoRq, Client client, Advert advert);

    BookingDtoRes toDtoRes(Booking booking);
}
