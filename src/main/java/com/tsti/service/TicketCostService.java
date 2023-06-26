package com.tsti.service;

import com.tsti.entity.Flight;
import com.tsti.entity.Passage;
import com.tsti.repository.FlightRepository;
import com.tsti.repository.PassageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketCostService implements ITicketCostService {

    private final PassageRepository passageRepository;

    @Autowired
    public TicketCostService(PassageRepository passageRepository) {
        this.passageRepository = passageRepository;
    }

    @Override
    public double getTicketCost(Long flightNumber, Long dni) throws Exception {
        Optional<Passage> dbPassage = passageRepository.findByDocumentAndFlightNumber(dni, flightNumber);
        if (dbPassage.isEmpty()) {
            throw new Exception("No se encontró el pasaje para el vuelo y DNI especificados");
        }
        Passage passage = dbPassage.get();

        // Calcular el costo total del pasaje
       return passage.getSeatNumber() * 1000;
    }
}
