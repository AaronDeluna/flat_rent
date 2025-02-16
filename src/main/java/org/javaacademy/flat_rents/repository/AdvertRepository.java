package org.javaacademy.flat_rents.repository;

import org.javaacademy.flat_rents.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepository extends JpaRepository<Advert, Integer> {
}
