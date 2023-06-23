package com.tsti.service;

import com.tsti.entity.Flight;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IFlightService {

	ResponseEntity<?> create(Flight flight) throws Exception;

	Optional<Flight> search(Long flightNumber) throws Exception;

	ResponseEntity<?> update(Flight flight) throws Exception;

	ResponseEntity<?> updateDateTime(Long flightNumber, LocalDateTime dateTime) throws Exception;

	ResponseEntity<?> delete(Long id) throws Exception;
}
