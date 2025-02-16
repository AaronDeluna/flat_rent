package org.javaacademy.flat_rents.repository;


import org.javaacademy.flat_rents.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
