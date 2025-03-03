package com.example.Online.Bus.Ticket.Booking.Application.service;
import com.example.Online.Bus.Ticket.Booking.Application.dto.BusDTO;
import java.util.List;
import java.util.Optional;

public interface BusService {
    // Basic CRUD operations
    List<BusDTO> getAllBuses();
    Optional<BusDTO> getBusById(Long id);
    BusDTO addBus(BusDTO busDTO);
    BusDTO updateBus(BusDTO busDTO);
    void deleteBus(Long id);

    // Additional methods
    List<BusDTO> findBusesByRoute(String source, String destination);
    List<BusDTO> findAvailableBuses(String source, String destination, String date);
    void updateSeatAvailability(Long busId, int seatsBooked);
}

