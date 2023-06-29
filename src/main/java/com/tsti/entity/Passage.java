package com.tsti.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.util.Random;

@Entity
public class Passage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Debe completar el documento")
    private Long document;
    @NotNull(message = "Debe completar el número de vuelo")
    private Long flightNumber;
    @NotNull(message = "Debe completar el número de asiento")
    private int seatNumber;

    private int cost;

    public Passage() {
    }

    public Passage(Long id, Long document, Long flightNumber, int seatNumber) {
        this.id = id;
        this.document = document;
        this.flightNumber = flightNumber;
        this.seatNumber = seatNumber;
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

    public int getCost() {
        Random random = new Random();
        int min = 1000;
        int max = 5000;
        cost = random.nextInt(max - min + 1) + min;
        return cost;
    }
}
