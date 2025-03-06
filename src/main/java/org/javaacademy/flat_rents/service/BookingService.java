package org.javaacademy.flat_rents.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.PageDto;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRes;
import org.javaacademy.flat_rents.dto.booking.BookingDtoRq;
import org.javaacademy.flat_rents.dto.client.ClientDto;
import org.javaacademy.flat_rents.entity.Booking;
import org.javaacademy.flat_rents.exception.DateConflictException;
import org.javaacademy.flat_rents.mapper.BookingMapper;
import org.javaacademy.flat_rents.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private static final int PAGE_SIZE = 10;
    private static final String NOT_CORRECT_RESERVED_DATE =
            "Даты бронирования пересекаются с уже существующей бронью для объявления с id: %s";
    private final BookingRepository bookingRepository;
    private final ClientService clientService;
    private final BookingMapper bookingMapper;

    @Transactional
    public BookingDtoRes save(BookingDtoRq bookingDtoRq) {
        ClientDto clientDto = bookingDtoRq.getClient();
        if (clientDto.getId() == null) {
            clientDto = clientService.save(clientDto);
        }

        bookingDtoRq.setClient(clientDto);
        Booking booking = bookingMapper.toEntityWithRelations(bookingDtoRq);
        checkBookingDateConflict(booking);

        booking.setTotalCost(calculateBookingPrice(
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getAdvert().getPrice()
        ));

        bookingRepository.save(booking);
        return bookingMapper.toDtoRes(booking);
    }

    public PageDto<BookingDtoRes> findAllByEmail(String email, Integer page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Booking> bookingPage = bookingRepository.findAllByClientEmail(email, pageable);

        List<BookingDtoRes> bookings = bookingMapper.toDtoResList(bookingPage.getContent());

        return new PageDto<>(bookings, bookingPage.getTotalPages(), page, PAGE_SIZE, bookingPage.getTotalElements());
    }

    private void checkBookingDateConflict(Booking newBooking) {
        Integer advertId = newBooking.getAdvert().getId();
        boolean conflictExists = bookingRepository
                .existsByAdvertIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        advertId,
                        newBooking.getStartDate(),
                        newBooking.getEndDate()
                );

        if (conflictExists) {
            throw new DateConflictException(NOT_CORRECT_RESERVED_DATE.formatted(advertId));
        }
    }

    private BigDecimal calculateBookingPrice(LocalDate startDate, LocalDate endDate, BigDecimal pricePerNight) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Дата окончания должна быть позже даты начала");
        }

        return pricePerNight.multiply(BigDecimal.valueOf(daysBetween));
    }
}
