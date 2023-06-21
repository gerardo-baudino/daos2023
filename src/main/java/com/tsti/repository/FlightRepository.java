package com.tsti.repository;

import com.tsti.entity.Flight;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

	Optional<Flight> findById(Long id);
	
	Optional<Flight> findByFlightNumber(Long flightNumber);
}
