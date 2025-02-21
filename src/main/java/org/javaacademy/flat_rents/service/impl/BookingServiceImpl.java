package org.javaacademy.flat_rents.service.impl;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final AdvertRepository advertRepository;

    @Override
    public BookingDtoRes create(CreateBookingDto createBookingDto) {
        Client client = clientRepository.findById(createBookingDto.getClientId()).orElseThrow(
                () -> new NotFoundException("Клиент с id: %s не найден")
        );
        Advert advert = advertRepository.findById(createBookingDto.getAdvertId()).orElseThrow(
                () -> new NotFoundException("Обьявление с id: %s не найдено")
        );
        Booking booking = bookingMapper.toEntity(createBookingDto);
        booking.setClient(client);
        booking.setAdvert(advert);

        bookingRepository.save(booking);
        return bookingMapper.toDtoRes(booking);
    }
}
