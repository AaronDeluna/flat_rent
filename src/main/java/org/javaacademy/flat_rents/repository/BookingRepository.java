package org.javaacademy.flat_rents.repository;


import org.javaacademy.flat_rents.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Page<Booking> findAllByClientEmail(String email, Pageable pageable);

    boolean existsByAdvertIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Integer advertId, LocalDate endDate, LocalDate startDate);

}
