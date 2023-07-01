package com.tsti.dto;

import com.tsti.entity.Passage;
import org.springframework.hateoas.RepresentationModel;

public class PassageDTO extends RepresentationModel<PassageDTO> {
    private Long id;
    private Long document;
    private Long flightNumber;
    private int seatNumber;

    public PassageDTO(Passage passage) {
        this.id = passage.getId();
        this.document = passage.getDocument();
        this.flightNumber = passage.getFlightNumber();
        this.seatNumber = passage.getSeatNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocument() {
        return document;
    }

    public void setDocument(Long document) {
        this.document = document;
    }

    public Long getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Long flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "Passage{" +
                "document=" + document +
                ", flightNumber=" + flightNumber +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
