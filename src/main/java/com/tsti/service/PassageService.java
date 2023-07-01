package com.tsti.service;

import com.tsti.entity.Client;
import com.tsti.entity.Flight;
import com.tsti.entity.Passage;
import com.tsti.repository.ClientRepository;
import com.tsti.repository.FlightRepository;
import com.tsti.repository.PassageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PassageService implements IPassageService {

    private final PassageRepository passageRepository;
    private final ClientRepository clientRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public PassageService(PassageRepository passageRepository, ClientRepository clientRepository, FlightRepository flightRepository) {
        this.passageRepository = passageRepository;
        this.clientRepository = clientRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public ResponseEntity<?> create(Passage passage) throws Exception {
        Optional<Client> dbClient = clientRepository.findByDocument(passage.getDocument());
        if (dbClient.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe un cliente con ese DNI");
        }
        Flight flight = flightRepository.findByFlightNumber(passage.getFlightNumber());
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe ese numero de vuelo");
        }
        int flightCapacity = flight.getSeatsPerRow() * flight.getNumRows();
        if (passage.getSeatNumber() > flightCapacity || passage.getSeatNumber() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe esa capacidad de asientos");
        }
        if (passageRepository.findBySeatNumberAndFlightNumber(passage.getSeatNumber(), passage.getFlightNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Asiento no disponible");
        }
        LocalDateTime dateNow = LocalDate.now().atStartOfDay();
        if (flight.getDateTime().isBefore(dateNow)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El fecha de vuelo corresponde a una fecha pasada");
        }
        Client client = dbClient.get();
        if (flight.getFlightType().equalsIgnoreCase("internacional") && client.getPassportNumber() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Para solicitar un pasaje internacional, el cliente debe registrar su pasaporte");
        }

        Passage savedPassage = passageRepository.save(passage);
        if (savedPassage.getId() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error inesperado, vuelva a intentar");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPassage);
    }

    @Override
    public Optional<Passage> search(Long document, Long flightNumber) throws Exception {
        return passageRepository.findByDocumentAndFlightNumber(document, flightNumber);
    }
}
