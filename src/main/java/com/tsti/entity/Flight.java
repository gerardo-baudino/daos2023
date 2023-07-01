package com.tsti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Flight {
    public static final String ORIGIN = "Aeropuerto Sauce Viejo";
    public static final String TYPE_INTERNATIONAL = "Internacional";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @NotNull(message = "Debe completar el número de vuelo")
    @Column(name = "FLIGHT_NUMBER")
    private Long flightNumber;
    @Column(name = "DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private String dateTime;
    @NotNull(message = "Debe completar el número de filas")
    @Column(name = "NUM_ROWS")
    private int numRows;
    @NotNull(message = "Debe completar el número de asientos por fila")
    @Column(name = "SEATS_PER_ROW")
    private int seatsPerRow;
    @NotNull(message = "Debe completar el tipo de vuelo: Nacional o Internacional")
    @Column(name = "FLIGHT_TYPE")
    private String flightType;
    @NotNull(message = "Debe completar el destino")
    @Column(name = "DESTINATION")
    private String destination;
    @Column(name = "ORIGEN")
    private String origin = ORIGIN;
    @Column(name = "STATUS")
    private String status;

    public Flight() {
    }

    public Flight(Long id, Long flightNumber, String dateTime, int numRows, int seatsPerRow, String flightType, String destination, String origin, String status) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.dateTime = dateTime;
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.flightType = flightType;
        this.destination = destination;
        this.origin = origin;
        this.status = status;
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
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            this.dateTime = null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.dateTime = dateTime.format(formatter);
        }
    }
}
