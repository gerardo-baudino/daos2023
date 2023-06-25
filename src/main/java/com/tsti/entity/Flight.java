package com.tsti.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;

@Entity
public class Flight {
	public static final String ORIGIN = "Aeropuerto Sauce Viejo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long flightNumber;
	@Column(columnDefinition = "DATETIME")
	private String dateTime;
	private int numRows;
	private int seatsPerRow;
	private String flightType;
	private String destination;
	private String origin = ORIGIN;
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
