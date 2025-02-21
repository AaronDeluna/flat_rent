package org.javaacademy.flat_rents.service;

import org.javaacademy.flat_rents.dto.advert.AdvertDtoRes;
import org.javaacademy.flat_rents.dto.advert.CreateAdvertDto;

public interface AdvertService {
    AdvertDtoRes create(CreateAdvertDto createAdvertDto);
}
