package org.javaacademy.flat_rents.repository;

import org.javaacademy.flat_rents.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
