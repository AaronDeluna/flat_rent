package org.javaacademy.flat_rents.repository;


import org.javaacademy.flat_rents.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByAdvertId(Integer advertId);

    Page<Booking> findAllByClientEmail(String email, Pageable pageable);
}
