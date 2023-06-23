package com.tsti.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Flight {
	private static final String ORIGIN = "Aeropuerto Sauce Viejo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long flightNumber;
	private LocalDateTime dateTime;
	private int numRows;
	private int seatsPerRow;
	private String flightType;
	private String destination;
	private String origin = ORIGIN;
	private String status;
	
	public Flight() {
    }

    public Flight(Long id, Long flightNumber, LocalDateTime dateTime, int numRows, int seatsPerRow, String flightType, String destination) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.dateTime = dateTime;
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.flightType = flightType;
        this.destination = destination;
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
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
}
