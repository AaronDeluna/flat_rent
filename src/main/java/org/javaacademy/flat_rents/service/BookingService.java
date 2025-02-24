package org.javaacademy.flat_rents.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRq;
import org.javaacademy.flat_rents.entity.Advert;
import org.javaacademy.flat_rents.entity.Booking;
import org.javaacademy.flat_rents.entity.Client;
import org.javaacademy.flat_rents.exception.NotFoundException;
import org.javaacademy.flat_rents.mapper.BookingMapper;
import org.javaacademy.flat_rents.repository.AdvertRepository;
import org.javaacademy.flat_rents.repository.BookingRepository;
import org.javaacademy.flat_rents.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingDtoRes save(BookingDtoRq bookingDtoRq) {
        Booking booking = bookingMapper.toEntityWithRelations(bookingDtoRq);

        booking.setTotalCost(calculateBookingPrice(
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getAdvert().getPrice()
        ));
        bookingRepository.save(booking);
        return bookingMapper.toDtoRes(booking);
    }

    private BigDecimal calculateBookingPrice(LocalDate startDate, LocalDate endDate, BigDecimal pricePerNight) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Дата окончания должна быть позже даты начала");
        }

        return pricePerNight.multiply(BigDecimal.valueOf(daysBetween));
    }
}
