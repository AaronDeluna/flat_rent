package org.javaacademy.flat_rents.repository;


import org.javaacademy.flat_rents.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Page<Booking> findAllByClientEmail(String email, Pageable pageable);

    @Query("""
            SELECT COUNT(b) > 0
            FROM Booking b
            WHERE b.advert.id = :advertId
            AND NOT (:endDate < b.startDate OR :startDate > b.endDate)""")
    boolean hasDateConflict(@Param("advertId") Integer advertId,
                            @Param("startDate") LocalDate startDate,
                            @Param("endDate") LocalDate endDate);

}
