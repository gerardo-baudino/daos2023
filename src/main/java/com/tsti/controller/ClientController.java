package com.tsti.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsti.controller.error.MessageError;
import com.tsti.dto.ClientDTO;
import com.tsti.entity.Client;
import com.tsti.exception.ExceptionCustom;
import com.tsti.service.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/client")
@RestController
@Validated
public class ClientController {
    private final IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatterError(result));
        }
        try {
            ResponseEntity<?> responseEntity = clientService.create(client);
            return ResponseEntity.status(responseEntity.getStatusCode())
                    .headers(responseEntity.getHeaders())
                    .body(buildResponse(client));
        } catch (ExceptionCustom exceptionCustom) {
            throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
        }
    }

    @GetMapping("/{document}")
    public ResponseEntity<?> searchClient(@PathVariable Long document) throws Exception {
        Optional<Client> clientOptional = clientService.search(document);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            return ResponseEntity.ok(buildResponse(client));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client client) throws Exception {
        try {
            ResponseEntity<?> responseEntity = clientService.update(client);
            Client updatedClient = (Client) responseEntity.getBody();

            return ResponseEntity.status(responseEntity.getStatusCode())
                    .headers(responseEntity.getHeaders())
                    .body(buildResponse(updatedClient));
        } catch (ExceptionCustom exceptionCustom) {
            throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@Valid @PathVariable Long id) throws Exception {
        try {
            clientService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ExceptionCustom exceptionCustom) {
            throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
        }
    }

    private String formatterError(BindingResult result) throws JsonProcessingException {
        List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
            Map<String, String> error = new HashMap<>();
            error.put(err.getField(), err.getDefaultMessage());
            return error;
        }).collect(Collectors.toList());
        MessageError e = new MessageError();
        e.setCode("01");
        e.setMenssage(errors);

        ObjectMapper maper = new ObjectMapper();
        return maper.writeValueAsString(e);
    }

    private ClientDTO buildResponse(Client client) throws ExceptionCustom {
        try {
            ClientDTO dto = new ClientDTO(client);
            Link selfLink = WebMvcLinkBuilder.linkTo(ClientController.class)
                    .slash(client.getDocument())
                    .withSelfRel();
            dto.add(selfLink);
            return dto;
        } catch (Exception e) {
            throw new ExceptionCustom(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
