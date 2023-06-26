package com.tsti.service;

public interface ITicketCostService {
    double getTicketCost(Long flightNumber, Long dni) throws Exception;
}
