package org.javaacademy.flat_rents.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.flat_rents.dto.client.ClientDto;
import org.javaacademy.flat_rents.entity.Client;
import org.javaacademy.flat_rents.mapper.ClientMapper;
import org.javaacademy.flat_rents.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        clientRepository.save(client);
        clientDto.setId(client.getId());
        return clientDto;
    }

}
