package com.tsti.service;

import com.tsti.entity.Client;
import com.tsti.entity.Passage;
import com.tsti.repository.PassageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassageService implements IPassageService {

    private final PassageRepository passageRepository;
    private final IClientService iClientService;
    private final IFlightService iFlightService;

    private final ITicketCostService iTicketCostService;

    public PassageService(PassageRepository passageRepository, IClientService iClientService, IFlightService iFlightService, ITicketCostService iTicketCostService) {
        this.passageRepository = passageRepository;
        this.iClientService = iClientService;
        this.iFlightService = iFlightService;
        this.iTicketCostService = iTicketCostService;
    }

    @Override
    public ResponseEntity<?> create(Passage passage) throws Exception {
        Optional<Client> dbClient = iClientService.search(passage.getDocument());
        if (dbClient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe un cliente con ese DNI");
        }
        Client client = dbClient.get();

        boolean isInternationalFlight = iFlightService.isInternationalFlight(passage.getFlightNumber());
        if (isInternationalFlight && !iClientService.validateClientForFlight(client, isInternationalFlight)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente necesita un pasaporte válido para vuelos internacionales");
        }

        if (!iFlightService.isValidFlight(passage.getFlightNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe vuelo para los datos suministrados");
        }

        if (isSeatAvailable(passage.getFlightNumber(), passage.getSeatNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Asiento no disponible");
        }

        Passage savedPassage = passageRepository.save(passage);
        if (savedPassage.getId() != null) {
            try {
                double cost = iTicketCostService.getTicketCost(passage.getFlightNumber(), passage.getDocument());
                return ResponseEntity.status(HttpStatus.CREATED).body("Costo: " + cost);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public Optional<Passage> search(Long document, Long flightNumber) throws Exception {
        return passageRepository.findByDocumentAndFlightNumber(document, flightNumber);
    }

    private boolean isSeatAvailable(Long flightNumber, int seatNumber) throws Exception {
        List<Passage> passages = passageRepository.findByFlightNumber(flightNumber);
        for (Passage passage : passages) {
            if (passage.getSeatNumber() == seatNumber) {
                return false; // Asiento ocupado
            }
        }
        return true;
    }
}
