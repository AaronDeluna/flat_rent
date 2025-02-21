package org.javaacademy.flat_rents.service.impl;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRes;
import org.javaacademy.flat_rents.dto.advert.CreateAdvertDto;
import org.javaacademy.flat_rents.entity.Advert;
import org.javaacademy.flat_rents.entity.Apartment;
import org.javaacademy.flat_rents.exception.NotFoundException;
import org.javaacademy.flat_rents.mapper.AdvertMapper;
import org.javaacademy.flat_rents.repository.AdvertRepository;
import org.javaacademy.flat_rents.repository.ApartmentRepository;
import org.javaacademy.flat_rents.service.AdvertService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private static final String APARTMENT_NOT_FOUND_MESSAGE = "Помещение с id: %s не найдено";
    private final AdvertMapper advertMapper;
    private final AdvertRepository advertRepository;
    private final ApartmentRepository apartmentRepository;

    @Override
    public AdvertDtoRes create(CreateAdvertDto createAdvertDto) {
        Apartment apartment = apartmentRepository.findById(createAdvertDto.getApartmentId()).orElseThrow(
                () -> new NotFoundException(APARTMENT_NOT_FOUND_MESSAGE.formatted(createAdvertDto.getApartmentId()))
        );
        Advert advert = advertMapper.toEntity(createAdvertDto);
        advert.setApartment(apartment);
        advertRepository.save(advert);
        return advertMapper.toResDto(advert);
    }
}
