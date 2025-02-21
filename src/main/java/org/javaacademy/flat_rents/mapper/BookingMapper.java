package org.javaacademy.flat_rents.mapper;

import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.CreateBookingDto;
import org.javaacademy.flat_rents.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClientMapper.class, AdvertMapper.class}
)
public interface BookingMapper {
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "advert", ignore = true)
    Booking toEntity(CreateBookingDto dto);
    @Mapping(target = "clientDto", source = "client")
    @Mapping(target = "createAdvertDto", source = "advert")
    BookingDtoRes toDtoRes(Booking booking);
}
