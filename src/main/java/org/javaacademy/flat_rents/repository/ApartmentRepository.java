package org.javaacademy.flat_rents.repository;

import org.javaacademy.flat_rents.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
}
