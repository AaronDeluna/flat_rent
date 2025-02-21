package org.javaacademy.flat_rents.service.impl;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.client.ClientDto;
import org.javaacademy.flat_rents.entity.Client;
import org.javaacademy.flat_rents.mapper.ClientMapper;
import org.javaacademy.flat_rents.repository.ClientRepository;
import org.javaacademy.flat_rents.service.ClientService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @Override
    public ClientDto create(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        clientRepository.save(client);
        clientDto.setId(client.getId());
        return clientDto;
    }
}
