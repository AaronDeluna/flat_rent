package org.javaacademy.flat_rents.service.impl;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;
import org.javaacademy.flat_rents.entity.Apartment;
import org.javaacademy.flat_rents.mapper.ApartmentMapper;
import org.javaacademy.flat_rents.repository.ApartmentRepository;
import org.javaacademy.flat_rents.service.ApartmentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    @Override
    public ApartmentDto create(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentMapper.toEntity(apartmentDto);
        apartmentRepository.save(apartment);
        apartmentDto.setId(apartmentDto.getId());
        return apartmentDto;
    }
}
