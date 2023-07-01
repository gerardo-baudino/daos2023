package com.tsti.dto;

import com.tsti.entity.Flight;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class FlightDTO extends RepresentationModel<FlightDTO> {
    private Long id;
    private Long flightNumber;
    private LocalDateTime dateTime;
    private int numRows;
    private int seatsPerRow;
    private String flightType;
    private String destination;
    private final String origin;
    private String status;

    public FlightDTO(Flight flight) {
        this.id = flight.getId();
        this.flightNumber = flight.getFlightNumber();
        this.dateTime = flight.getDateTime();
        this.numRows = flight.getNumRows();
        this.seatsPerRow = flight.getSeatsPerRow();
        this.flightType = flight.getFlightType();
        this.destination = flight.getDestination();
        this.origin = flight.getOrigin();
        this.status = flight.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Long flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber=" + flightNumber +
                ", dateTime=" + dateTime +
                ", numRows=" + numRows +
                ", seatsPerRow=" + seatsPerRow +
                ", flightType='" + flightType + '\'' +
                ", destination='" + destination + '\'' +
                ", origin='" + origin + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
