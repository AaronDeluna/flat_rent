package org.javaacademy.flat_rents.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.CreateBookingDto;
import org.javaacademy.flat_rents.entity.Advert;
import org.javaacademy.flat_rents.entity.Booking;
import org.javaacademy.flat_rents.entity.Client;
import org.javaacademy.flat_rents.exception.NotFoundException;
import org.javaacademy.flat_rents.mapper.BookingMapper;
import org.javaacademy.flat_rents.repository.AdvertRepository;
import org.javaacademy.flat_rents.repository.BookingRepository;
import org.javaacademy.flat_rents.repository.ClientRepository;
import org.javaacademy.flat_rents.service.BookingService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    public static final String ADVERT_NOT_FOUND_MESSAGE = "Объявление с id: %s не найдено.";
    public static final String CLIENT_NOT_FOUND_MESSAGE = "Клиент с id: %s не найден";

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final AdvertRepository advertRepository;

    @Override
    public BookingDtoRes create(CreateBookingDto createBookingDto) {
        Client client = clientRepository.findById(createBookingDto.getClientId()).orElseThrow(
                () -> new NotFoundException(CLIENT_NOT_FOUND_MESSAGE.formatted(createBookingDto.getClientId()))
        );
        Advert advert = advertRepository.findById(createBookingDto.getAdvertId()).orElseThrow(
                () -> new NotFoundException(ADVERT_NOT_FOUND_MESSAGE.formatted(createBookingDto.getAdvertId()))
        );
        Booking booking = bookingMapper.toEntity(createBookingDto);
        booking.setClient(client);
        booking.setAdvert(advert);

        bookingRepository.save(booking);
        return bookingMapper.toDtoRes(booking);
    }
}
