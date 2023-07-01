package com.tsti.service;

import java.time.LocalDateTime;
import com.tsti.entity.Flight;

public class FlightStatusManager {
	private static final String STATUS_REGISTERED = "Registrado";
	private static final String STATUS_RESCHEDULED = "Reprogramado";
	private static final String STATUS_CANCELLED = "Cancelado";

	public static void registerFlight(Flight flight) {
		flight.setStatus(STATUS_REGISTERED);
	}
	
	public static void rescheduleFlight(Flight flight) {
		flight.setStatus(STATUS_RESCHEDULED);
	}

	public static void cancelFlight(Flight flight) {
		flight.setStatus(STATUS_CANCELLED);
	}

	public static void updateFlightStatus(Flight flight) {
		if (flight.getStatus().equals(STATUS_RESCHEDULED) || flight.getStatus().equals(STATUS_CANCELLED)) {
			return;
		}

		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime flightDateTime = flight.getDateTime();

		if (flightDateTime == null) {
			flight.setDateTime(currentDateTime);
			flight.setStatus(STATUS_REGISTERED);
		} else if (currentDateTime.isAfter(flightDateTime)) {
			flight.setStatus(STATUS_REGISTERED);
		} else {
			flight.setStatus(STATUS_RESCHEDULED);
		}
	}
	
	public static boolean isFlightRegistered(Flight flight) {
		return flight.getStatus().equals(STATUS_REGISTERED);
	}

	public static boolean isFlightCanceled(Flight flight) {
		return flight.getStatus().equals(STATUS_CANCELLED);
	}
}
