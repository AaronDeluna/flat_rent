package org.javaacademy.flat_rents.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.apartment.ApartmentDto;
import org.javaacademy.flat_rents.entity.Apartment;
import org.javaacademy.flat_rents.mapper.ApartmentMapper;
import org.javaacademy.flat_rents.repository.ApartmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;

    public ApartmentDto save(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentMapper.toEntity(apartmentDto);
        apartmentRepository.save(apartment);
        apartmentDto.setId(apartment.getId());
        return apartmentDto;
    }
}
