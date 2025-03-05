package org.javaacademy.flat_rents.mapper;

import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRq;
import org.javaacademy.flat_rents.entity.Advert;
import org.javaacademy.flat_rents.entity.Booking;
import org.javaacademy.flat_rents.entity.Client;
import org.javaacademy.flat_rents.exception.EntityNotFoundException;
import org.javaacademy.flat_rents.repository.AdvertRepository;
import org.javaacademy.flat_rents.repository.ClientRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ClientMapper.class, AdvertMapper.class}
)
public abstract class BookingMapper {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdvertRepository advertRepository;

    @Mapping(target = "totalCost", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "client")
    @Mapping(target = "advert", source = "advert")
    public abstract Booking toEntity(BookingDtoRq dtoRq, Client client, Advert advert);

    public Booking toEntityWithRelations(BookingDtoRq dtoRq) {
        Integer clientId = dtoRq.getClient().getId();
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new EntityNotFoundException("Клиент с id: %s не найден".formatted(clientId))
        );

        Advert advert = advertRepository.findById(dtoRq.getAdvertId()).orElseThrow(
                () -> new EntityNotFoundException("Обьявление с id: %s не найдено".formatted(dtoRq.getAdvertId()))
        );

        return toEntity(dtoRq, client, advert);
    }

    public abstract List<BookingDtoRes> toDtoResList(List<Booking> bookingList);

    public abstract BookingDtoRes toDtoRes(Booking booking);
}
