package com.tsti.controller;

import com.tsti.entity.Client;
import com.tsti.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/client")
@RestController
public class ClientController {

    private IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client) throws Exception {
        return clientService.create(client);
    }

    @GetMapping("/{document}")
    public Optional<Client> searchClient(@PathVariable Long document) throws Exception {
        return clientService.search(document);
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody Client client) throws Exception {
        return clientService.update(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) throws Exception {
        return clientService.delete(id);
    }
}
