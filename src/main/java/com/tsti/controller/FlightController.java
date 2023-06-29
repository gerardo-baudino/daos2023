package com.tsti.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsti.controller.error.MessageError;
import com.tsti.exception.ExceptionCustom;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsti.entity.Flight;
import com.tsti.service.IFlightService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/flights")
@Validated
public class FlightController {

	private IFlightService flightService;

	@Autowired
	public FlightController(IFlightService flightService) {
		this.flightService = flightService;
	}

	@PostMapping
	public ResponseEntity<?> createFlight(@Valid @RequestBody Flight flight, BindingResult result) throws Exception {
		if(result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatterError(result));
		}
		try {
			ResponseEntity<?> responseEntity = flightService.create(flight);
			Link selfLink = Link.of("/flights/" + flight.getFlightNumber());
			EntityModel<?> resourceWithLink = EntityModel.of(responseEntity.getBody(), selfLink);

			return ResponseEntity.status(responseEntity.getStatusCode())
					.headers(responseEntity.getHeaders())
					.body(resourceWithLink);
		} catch (ExceptionCustom exceptionCustom){
			throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
		}
	}

	@GetMapping("/{flightNumber}")
	public ResponseEntity<?> getFlightByFlightNumber(@PathVariable Long flightNumber) throws Exception {
		Optional<Flight> flightOptional = flightService.search(flightNumber);
		if (flightOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Flight flight = flightOptional.get();
		Link selfLink = Link.of("/flights/" + flight.getFlightNumber());
		EntityModel<Flight> resourceWithLink = EntityModel.of(flight, selfLink);

		return ResponseEntity.ok(resourceWithLink);
	}

	@PutMapping("/{flightNumber}/datetime")
	public ResponseEntity<?> updateFlightDateTime(@PathVariable Long flightNumber, @RequestBody LocalDateTime dateTime)
			throws Exception {
		try {
		ResponseEntity<?> responseEntity = flightService.updateDateTime(flightNumber, dateTime);
		Link selfLink = Link.of("/flights/" + flightNumber + "/datetime");
		EntityModel<?> resourceWithLink = EntityModel.of(responseEntity.getBody(), selfLink);

		return ResponseEntity.status(responseEntity.getStatusCode())
				.headers(responseEntity.getHeaders())
				.body(resourceWithLink);
		} catch (ExceptionCustom exceptionCustom){
			throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelFlight(@PathVariable Long id) throws Exception {
		try {
		ResponseEntity<?> responseEntity = flightService.delete(id);
		Link selfLink = Link.of("/flights/" + id);
		EntityModel<?> resourceWithLink = EntityModel.of(responseEntity.getBody(), selfLink);

		return ResponseEntity.status(responseEntity.getStatusCode())
				.headers(responseEntity.getHeaders())
				.body(resourceWithLink);
	} catch (ExceptionCustom exceptionCustom){
		throw new ExceptionCustom(exceptionCustom.getMessage(), exceptionCustom.getStatusCode());
	 }
	}
	private String formatterError(BindingResult result) throws JsonProcessingException {
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

