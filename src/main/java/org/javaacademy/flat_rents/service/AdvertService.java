package org.javaacademy.flat_rents.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.PageDto;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRes;
import org.javaacademy.flat_rents.dto.advert.AdvertDtoRq;
import org.javaacademy.flat_rents.entity.Advert;
import org.javaacademy.flat_rents.entity.Apartment;
import org.javaacademy.flat_rents.exception.EntityNotFoundException;
import org.javaacademy.flat_rents.mapper.AdvertMapper;
import org.javaacademy.flat_rents.repository.AdvertRepository;
import org.javaacademy.flat_rents.repository.ApartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private static final int PAGE_SIZE = 10;
    private static final String SORT_BY_PRICE = "price";

    private final ApartmentRepository apartmentRepository;
    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;

    public AdvertDtoRes save(AdvertDtoRq advertDtoRq) {
        Apartment apartment = apartmentRepository.findById(advertDtoRq.getApartmentId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Помещение с id: %s не найдено".formatted(advertDtoRq.getApartmentId()))
        );
        Advert advert = advertMapper.toEntity(advertDtoRq, apartment);
        advertRepository.save(advert);
        return advertMapper.toDtoRes(advert);
    }

    public PageDto<AdvertDtoRes> findAllByCity(String city, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(SORT_BY_PRICE).descending());
        Page<Advert> advertPage = advertRepository.findAllByApartmentCity(city, pageable);

        List<AdvertDtoRes> adverts = advertMapper.toDtoResList(advertPage.getContent());

        return new PageDto<>(adverts, advertPage.getTotalPages(), page, PAGE_SIZE, advertPage.getTotalElements());
    }

}
