package com.tsti.service;

import com.tsti.entity.Client;
import com.tsti.exception.ExceptionCustom;
import com.tsti.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ResponseEntity<?> create(Client client) throws Exception {
        Optional<Client> dbClient = clientRepository.findByDocument(client.getDocument());
        if (dbClient.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un cliente registrado con ese DNI");
        }
        Client savedClient = clientRepository.save(client);
        if (savedClient.getId() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error inesperado, vuelva a intentar");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @Override
    public Optional<Client> search(Long document) throws Exception {
        return clientRepository.findByDocument(document);
    }

    @Override
    public ResponseEntity<?> update(Client client) throws Exception {
        Optional<Client> dbClient = clientRepository.findByDocument(client.getDocument());
        if (dbClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verifique no alterar el DNI o que el mismo se encuentre ya registrado");
        }
        Client savedClient = clientRepository.save(client);
        if (savedClient.getId() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error inesperado, vuelva a intentar");
        }
        return ResponseEntity.status(HttpStatus.OK).body(savedClient);
    }

    @Override
    public ResponseEntity<?> delete(Long id) throws Exception {
        Optional<Client> dbClient = clientRepository.findById(id);
        if (dbClient.isPresent()) {
            clientRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe un cliente con ese ID");
    }
}
