package com.tsti.repository;

import com.tsti.entity.Flight;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlightRepository extends JpaRepository<Flight, Long> {

	Optional<Flight> findById(Long id);

	@Query("select f from Flight f where f.flightNumber = ?1")
	Flight findByFlightNumber(Long flightNumber);
}
