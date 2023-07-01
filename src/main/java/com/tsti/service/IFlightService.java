package com.tsti.service;

import com.tsti.entity.Flight;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IFlightService {

    ResponseEntity<?> create(Flight flight) throws Exception;

    Flight search(Long flightNumber) throws Exception;

    ResponseEntity<?> update(Flight flight) throws Exception;

    ResponseEntity<?> delete(Long flightNumber) throws Exception;

    boolean isInternationalFlight(Long flightNumber) throws Exception;

    boolean isValidFlight(Long flightNumber) throws Exception;
}
