package com.example.Online.Bus.Ticket.Booking.Application.service;

import com.example.Online.Bus.Ticket.Booking.Application.dto.PassengerDTO;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Gender;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Passenger;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PassengerService {
    // Basic CRUD operations
    PassengerDTO addPassenger(PassengerDTO passengerDTO);
   // List<PassengerDTO> getPassengersByBookingId(Long bookingId);
    void deletePassenger(Long id);

    // Additional methods
    PassengerDTO updatePassenger(Long passengerId, PassengerDTO passengerDTO);
    List<PassengerDTO> getAllPassengers();
}

