package com.tsti.service;

import com.tsti.entity.Client;
import com.tsti.entity.Passage;
import com.tsti.repository.PassageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PassageService implements IPassageService {

    private PassageRepository passageRepository;
    @Autowired
    public PassageService(PassageRepository passageRepository) {
        this.passageRepository = passageRepository;
    }

    @Override
    public ResponseEntity<?> create(Passage passage) throws Exception {
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
