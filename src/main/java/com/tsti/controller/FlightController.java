package com.tsti.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsti.controller.error.MessageError;
import com.tsti.dto.FlightDTO;
import com.tsti.entity.Flight;
import com.tsti.exception.ExceptionCustom;
import com.tsti.service.IFlightService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flight")
@Validated
public class FlightController {
    private final IFlightService flightService;

    @Autowired
    public FlightController(IFlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<?> createFlight(@Valid @RequestBody Flight flight, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatterError(result));
        }
        try {
            ResponseEntity<?> responseEntity = flightService.create(flight);
            return ResponseEntity.status(responseEntity.getStatusCode())
                    .headers(responseEntity.getHeaders())
                    .body(buildResponse(flight));
        } catch (ExceptionCustom exceptionCustom) {
            throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
        }
    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<?> getFlightByFlightNumber(@PathVariable Long flightNumber) throws Exception {
        Flight flight = flightService.search(flightNumber);
        if (flight != null) {
            return ResponseEntity.ok(buildResponse(flight));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<?> updateFlightDateTime(@RequestBody Flight flight)
            throws Exception {
        try {
            ResponseEntity<?> responseEntity = flightService.update(flight);
            Flight updatedFlight = (Flight) responseEntity.getBody();

            return ResponseEntity.status(responseEntity.getStatusCode())
                    .headers(responseEntity.getHeaders())
                    .body(buildResponse(updatedFlight));
        } catch (ExceptionCustom exceptionCustom) {
            throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
        }
    }

    @DeleteMapping("/{flightNumber}")
    public ResponseEntity<?> cancelFlight(@PathVariable Long flightNumber) throws Exception {
        try {
            ResponseEntity<?> responseEntity = flightService.delete(flightNumber);
            Flight flight = (Flight) responseEntity.getBody();

            return ResponseEntity.status(responseEntity.getStatusCode())
                    .headers(responseEntity.getHeaders())
                    .body(buildResponse(flight));
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

    private FlightDTO buildResponse(Flight flight) throws ExceptionCustom {
        try {
            FlightDTO dto = new FlightDTO(flight);
            Link selfLink = WebMvcLinkBuilder.linkTo(FlightController.class)
                    .slash(flight.getFlightNumber())
                    .withSelfRel();
            dto.add(selfLink);
            return dto;
        } catch (Exception e) {
            throw new ExceptionCustom(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

