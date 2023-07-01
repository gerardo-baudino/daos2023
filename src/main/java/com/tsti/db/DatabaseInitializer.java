package com.tsti.db;

import com.tsti.entity.Client;
import com.tsti.entity.Flight;
import com.tsti.entity.Passage;
import com.tsti.repository.ClientRepository;
import com.tsti.repository.FlightRepository;
import com.tsti.repository.PassageRepository;
import com.tsti.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;


@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final FlightRepository flightRepository;
    private final PassageRepository passageRepository;

    @Autowired
    public DatabaseInitializer(ClientRepository clientRepository, FlightRepository flightRepository, PassageRepository passageRepository, IClientService clientService) {
        this.clientRepository = clientRepository;
        this.flightRepository = flightRepository;
        this.passageRepository = passageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadDBClient();
        loadDBFlight();
        loadDBPassage();
    }

    public void loadDBClient() throws ParseException {
        List<Client> clientList = clientRepository.findAll();
        if (clientList.size() == 0) {
            dbClient();
        }
    }

    public void loadDBFlight() {
        List<Flight> flightList = flightRepository.findAll();
        if (flightList.size() == 0) {
            dbFlight();
        }
    }

    public void loadDBPassage() {
        List<Passage> passageList = passageRepository.findAll();
        if (passageList.size() == 0) {
            dbPassage();
        }
    }

    public void dbClient() throws ParseException {
        clientRepository.save(new Client(null, 20000000L, "juan", "gomez", "Calle 1", "juan.gomez@gmail.com", "1980-01-01"));
        clientRepository.save(new Client(null, 20000001L, "maria", "perez", "Calle 2", "maria.perez@gmail.com", "1980-02-02"));
        clientRepository.save(new Client(null, 20000002L, "jose", "garcia", "Calle 3", "jose.garcia@gmail.com", "1980-03-03"));
        clientRepository.save(new Client(null, 20000003L, "ana", "fernandez", "Calle 4", "ana.fernandez@gmail.com", "1980-04-04", "10020", "2025-04-30"));
        clientRepository.save(new Client(null, 20000004L, "pedro", "gonzalez", "Calle 5", "pedro.gonzalez@gmail.com", "1980-05-05", "30060", "2028-11-03"));
    }

    private void dbFlight() {
        flightRepository.save(new Flight(null, 1L, "2025-01-05T23:20:00", 1, 2, "Internacional", "Brasil", Flight.ORIGIN, "Registrado"));
        flightRepository.save(new Flight(null, 2L, "2025-03-28T10:10:00", 2, 2, "Internacional", "Mexico", Flight.ORIGIN, "Registrado"));
        flightRepository.save(new Flight(null, 3L, "2025-04-30T15:45:00", 3, 2, "Nacional", "Salta", Flight.ORIGIN, "Registrado"));
    }

    private void dbPassage() {
        passageRepository.save(new Passage(null, 20000000L, 1L, 1));
        passageRepository.save(new Passage(null, 20000000L, 2L, 1));
        passageRepository.save(new Passage(null, 20000000L, 3L, 1));
        passageRepository.save(new Passage(null, 20000001L, 1L, 2));
        passageRepository.save(new Passage(null, 20000001L, 2L, 2));
        passageRepository.save(new Passage(null, 20000001L, 3L, 2));
        passageRepository.save(new Passage(null, 20000002L, 1L, 3));
        passageRepository.save(new Passage(null, 20000002L, 2L, 3));
        passageRepository.save(new Passage(null, 20000002L, 3L, 3));
        passageRepository.save(new Passage(null, 20000003L, 1L, 4));
        passageRepository.save(new Passage(null, 20000003L, 2L, 4));
        passageRepository.save(new Passage(null, 20000003L, 3L, 4));
        passageRepository.save(new Passage(null, 20000004L, 1L, 5));
        passageRepository.save(new Passage(null, 20000004L, 2L, 5));
        passageRepository.save(new Passage(null, 20000004L, 3L, 5));
    }
}
