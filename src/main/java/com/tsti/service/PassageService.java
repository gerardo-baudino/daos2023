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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class PassageService implements IPassageService {

    private PassageRepository passageRepository;
    private ClientRepository clientRepository;
    private FlightRepository flightRepository;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe un cliente con ese DNI");
        }
        Optional<Flight> dbFlight = flightRepository.findByFlightNumber(passage.getFlightNumber());
        Flight flight = dbFlight.get();
        LocalDateTime dateNow = LocalDate.now().atStartOfDay();
        if (dbFlight.isEmpty() && flight.getDateTime().compareTo(dateNow)< 0 ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ese numero de vuelo");
        }
        if (dbFlight.isPresent() && dbClient.isPresent()){

            Client client = dbClient.get();
            if(flight.getFlightType() == "internacional" && client.getPassportNumber() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Para solicitar un pasaje internacional, el cliente debe registrar su pasaporte");
            }
            int flightCapacity = flight.getSeatsPerRow()* flight.getNumRows();
            if (passage.getSeatNumber()>flightCapacity || passage.getSeatNumber()<0 ){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe esa capacidad de asientos");
            }
            if(passageRepository.findBySeatNumberAndFlightNumber(passage.getSeatNumber() ,passage.getFlightNumber()).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Asiento no disponible");
            }
        }


        Passage savedPassage = passageRepository.save(passage);
        if (savedPassage.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public Optional<Passage> search(Long document, Long flightNumber) throws Exception {
        return passageRepository.findByDocumentAndFlightNumber(document, flightNumber);
    }
}
