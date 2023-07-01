package com.tsti.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsti.controller.error.MessageError;
import com.tsti.dto.PassageDTO;
import com.tsti.entity.Passage;
import com.tsti.exception.ExceptionCustom;
import com.tsti.service.IPassageService;
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

@RequestMapping("/passage")
@RestController
@Validated
public class PassageController {
    private final IPassageService passageService;

    @Autowired
    public PassageController(IPassageService passageService) {
        this.passageService = passageService;
    }

    @PostMapping
    public ResponseEntity<?> createPassage(@Valid @RequestBody Passage passage, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatterError(result));
        }
        try {
            ResponseEntity<?> responseEntity = passageService.create(passage);
            return ResponseEntity.status(responseEntity.getStatusCode()).headers(responseEntity.getHeaders()).body((buildResponse(passage)));
        } catch (ExceptionCustom exceptionCustom) {
            throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
        }
    }

    @GetMapping("/{document}/{flightNumber}")
    public ResponseEntity<?> searchPassage(@PathVariable Long document, @PathVariable Long flightNumber) throws Exception {
        Optional<Passage> passageOptional = passageService.search(document, flightNumber);
        if (passageOptional.isPresent()) {
            Passage passage = passageOptional.get();
            return ResponseEntity.ok(buildResponse(passage));
        } else {
            return ResponseEntity.notFound().build();
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

    private PassageDTO buildResponse(Passage passage) throws ExceptionCustom {
        try {
            PassageDTO dto = new PassageDTO(passage);
            Link selfLink = WebMvcLinkBuilder.linkTo(PassageController.class)
                    .slash(passage.getFlightNumber())
                    .withSelfRel();
            Link clientLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientController.class)
                    .searchClient(passage.getDocument()))
                    .withRel("client");
            Link flightLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightController.class)
                    .getFlightByFlightNumber(passage.getFlightNumber()))
                    .withRel("flight");
            Link costLink = Link.of("Costo de Pasaje: $" + passage.getCost())
                    .withRel("cost");;
            dto.add(selfLink);
            dto.add(clientLink);
            dto.add(flightLink);
            dto.add(costLink);
            return dto;
        } catch (Exception e) {
            throw new ExceptionCustom(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
