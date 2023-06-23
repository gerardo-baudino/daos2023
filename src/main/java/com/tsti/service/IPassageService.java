package com.tsti.service;

import com.tsti.entity.Passage;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IPassageService {

    ResponseEntity<?> create(Passage passage) throws Exception;

    Optional<Passage> search(Long document, Long flightNumber) throws Exception;
}
