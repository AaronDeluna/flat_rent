package org.javaacademy.flat_rents.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRes;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRq;
import org.javaacademy.flat_rents.entity.Advert;
import org.javaacademy.flat_rents.entity.Apartment;
import org.javaacademy.flat_rents.exception.NotFoundException;
import org.javaacademy.flat_rents.mapper.AdvertMapper;
import org.javaacademy.flat_rents.repository.AdvertRepository;
import org.javaacademy.flat_rents.repository.ApartmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private final ApartmentRepository apartmentRepository;
    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;

    public AdvertDtoRes save(AdvertDtoRq advertDtoRq) {
        Apartment apartment = apartmentRepository.findById(advertDtoRq.getApartmentId()).orElseThrow(
                () -> new NotFoundException("Помещение с id: %s не найдено".formatted(advertDtoRq.getApartmentId()))
        );
        Advert advert = advertMapper.toEntity(advertDtoRq, apartment);
        advertRepository.save(advert);
        return advertMapper.toDtoRes(advert);
    }
}
