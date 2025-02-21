package org.javaacademy.flat_rents.service;

import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.CreateBookingDto;

public interface BookingService {
    BookingDtoRes create(CreateBookingDto createBookingDto);
}
