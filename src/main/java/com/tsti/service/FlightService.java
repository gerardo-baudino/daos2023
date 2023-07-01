package com.tsti.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tsti.entity.Flight;
import com.tsti.repository.FlightRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlightService implements IFlightService {

	private FlightRepository flightRepository;

	@Autowired
	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	@Override
	public ResponseEntity<?> create(Flight flight) throws Exception {
		Flight existingFlight = flightRepository.findByFlightNumber(flight.getFlightNumber());
		if (existingFlight != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un vuelo con el mismo número");
		}
		FlightStatusManager.registerFlight(flight);

		Flight savedFlight = flightRepository.save(flight);
		if (savedFlight.getId() == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error inesperado, vuelva a intentar");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(savedFlight);
	}

	@Override
	public Flight search(Long flightNumber) throws Exception {
		return flightRepository.findByFlightNumber(flightNumber);
	}

	@Override
	public ResponseEntity<?> update(Flight flight) throws Exception {
		Flight updatedFlight = flightRepository.findByFlightNumber(flight.getFlightNumber());
		if (updatedFlight == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vuelo no encontrado");
		}
		if (FlightStatusManager.isFlightRegistered(updatedFlight)) {
			updatedFlight.setDateTime(flight.getDateTime());
		}

		FlightStatusManager.updateFlightStatus(updatedFlight);

		Flight savedFlight = flightRepository.save(updatedFlight);
		if (savedFlight.getId() == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error inesperado, vuelva a intentar");
		}
		return ResponseEntity.status(HttpStatus.OK).body(savedFlight);
	}

	@Override
	public ResponseEntity<?> delete(Long flightNumber) throws Exception {
		Flight flight = flightRepository.findByFlightNumber(flightNumber);
		if (flight == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vuelo no encontrado");
		}
		if (FlightStatusManager.isFlightCanceled(flight)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El Vuelo ya se encuentra cancelado");
		}
		FlightStatusManager.cancelFlight(flight);

		Flight savedFlight = flightRepository.save(flight);
		if (savedFlight.getId() == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error inesperado, vuelva a intentar");
		}
		return ResponseEntity.status(HttpStatus.OK).body(savedFlight);
	}

	@Override
	public boolean isInternationalFlight(Long flightNumber) throws Exception {
		Optional<Flight> flightOptional = flightRepository.findById(flightNumber);
		if (flightOptional.isPresent()) {
			Flight flight = flightOptional.get();
			return Flight.TYPE_INTERNATIONAL.equalsIgnoreCase(flight.getFlightType());
		}
		return false;
	}

	@Override
	public boolean isValidFlight(Long flightNumber) throws Exception {
		// Verificar existencia del vuelo
		Optional<Flight> flightOptional = flightRepository.findById(flightNumber);
		if (flightOptional.isEmpty()) {
			return false;
		}
		Flight flight = flightOptional.get();

		// Verificar si el vuelo es futuro (no pasado)
		LocalDateTime currentDateTime = LocalDateTime.now();
		return !flight.getDateTime().isBefore(currentDateTime);
	}
}