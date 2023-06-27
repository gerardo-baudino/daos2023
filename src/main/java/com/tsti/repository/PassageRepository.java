package com.tsti.repository;


import com.tsti.entity.Passage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassageRepository extends JpaRepository <Passage, Long> {

    Optional<Passage> findByDocumentAndFlightNumber(Long document, Long flightNumber) ;
    Optional<Passage> findBySeatNumberAndFlightNumber(int seatNumber, Long flightNumber) ;

}
