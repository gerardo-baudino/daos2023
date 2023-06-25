package com.tsti.service;

import com.tsti.entity.Client;
import com.tsti.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService implements IClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ResponseEntity<?> create(Client client) throws Exception {
        Optional<Client> dbClient = search(client.getDocument());
        if (!dbClient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un cliente registrado con ese DNI");
        }
        Client savedClient = clientRepository.save(client);
        if (savedClient.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public Optional<Client> search(Long document) throws Exception {
        return clientRepository.findByDocument(document);
    }

    @Override
    public ResponseEntity<?> update(Client client) throws Exception {
        Optional<Client> dbClient = search(client.getDocument());
        if (dbClient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique no alterar el DNI o que el mismo se encuentre ya registrado");
        }
        Client savedClient = clientRepository.save(client);
        if (savedClient.getId() != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> delete(Long id) throws Exception {
        Optional<Client> dbClient = clientRepository.findById(id);
        if (!dbClient.isEmpty()) {
            clientRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe un cliente con ese ID");
    }
}
