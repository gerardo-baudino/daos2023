package com.tsti.service;

import com.tsti.entity.Client;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IClientService {

     ResponseEntity<?> create(Client client) throws Exception;

     Optional<Client> search(Long document) throws Exception;

     ResponseEntity<?> update(Client client) throws Exception;

     ResponseEntity<?> delete(Long id) throws Exception;
}
