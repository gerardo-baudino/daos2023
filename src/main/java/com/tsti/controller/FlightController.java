package com.tsti.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/flights")
public class FlightController {

	private IFlightService flightService;

	@Autowired
	public FlightController(IFlightService flightService) {
		this.flightService = flightService;
	}

	@PostMapping
	public ResponseEntity<?> createFlight(@RequestBody Flight flight) throws Exception {
		return flightService.create(flight);
	}

	@GetMapping("/{flightNumber}")
	public ResponseEntity<?> getFlightByFlightNumber(@PathVariable Long flightNumber) throws Exception {
		Optional<Flight> flightOptional = flightService.search(flightNumber);
		if (flightOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(flightOptional.get());
	}

	@PutMapping("/{flightNumber}/datetime")
	public ResponseEntity<?> updateFlightDateTime(@PathVariable Long flightNumber, @RequestBody LocalDateTime dateTime)
			throws Exception {
		return flightService.updateDateTime(flightNumber, dateTime);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelFlight(@PathVariable Long id) throws Exception {
		return flightService.delete(id);
	}
}
