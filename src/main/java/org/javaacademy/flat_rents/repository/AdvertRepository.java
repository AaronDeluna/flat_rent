package org.javaacademy.flat_rents.repository;

import org.javaacademy.flat_rents.entity.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advert, Integer> {
    Page<Advert> findAllByApartmentCity(String city, Pageable pageable);
}
