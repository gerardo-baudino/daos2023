package com.tsti.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsti.controller.error.MessageError;
import com.tsti.entity.Client;
import com.tsti.exception.ExceptionCustom;
import com.tsti.service.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    private IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult result) throws Exception {
        if(result.hasErrors())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatterError(result));
        }
        try {
            ResponseEntity<?> responseEntity = clientService.create(client);
            Link selfLink = Link.of("/client/" + client.getDocument());
            EntityModel<?> resourceWithLink = EntityModel.of(responseEntity.getBody(), selfLink);

            return ResponseEntity.status(responseEntity.getStatusCode())
                    .headers(responseEntity.getHeaders())
                    .body(resourceWithLink);
        } catch (ExceptionCustom exceptionCustom){
            throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
        }
    }

    @GetMapping("/{document}")
    public ResponseEntity<?> searchClient(@PathVariable Long document) throws Exception {
        Optional<Client> clientOptional = clientService.search(document);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();

            Link selfLink = Link.of("/client/" + client.getId());
            EntityModel<Client> resourceWithLink = EntityModel.of(client, selfLink);

            return ResponseEntity.ok(resourceWithLink);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client client) throws Exception {
        try {
        ResponseEntity<?> responseEntity = clientService.update(client);

        Link selfLink = Link.of("/client/" + client.getId());
        EntityModel<?> resourceWithLink = EntityModel.of(responseEntity.getBody(), selfLink);

        return ResponseEntity.status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body(resourceWithLink);
        } catch (ExceptionCustom exceptionCustom){
            throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@Valid @PathVariable Long id) throws Exception {
        try {
        ResponseEntity<?> responseEntity = clientService.delete(id);

        Link selfLink = Link.of("/client/" + id);
        EntityModel<?> resourceWithLink = EntityModel.of(responseEntity.getBody(), selfLink);

        return ResponseEntity.status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body(resourceWithLink);
    } catch (ExceptionCustom exceptionCustom){
        throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
     }
    }
    private String formatterError(BindingResult result) throws JsonProcessingException
    {
        List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
            Map<String, String> error= new HashMap<>();
            error.put(err.getField(),err.getDefaultMessage() );
            return error;
        }).collect(Collectors.toList());
        MessageError e = new MessageError();
        e.setCode("01");
        e.setMenssage(errors);

        ObjectMapper maper = new ObjectMapper();
        String json = maper.writeValueAsString(e);
        return json;
    }
}
