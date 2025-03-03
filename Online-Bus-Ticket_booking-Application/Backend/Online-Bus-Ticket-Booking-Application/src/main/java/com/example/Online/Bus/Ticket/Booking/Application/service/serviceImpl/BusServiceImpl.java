package com.example.Online.Bus.Ticket.Booking.Application.service.serviceImpl;

import com.example.Online.Bus.Ticket.Booking.Application.dto.BusDTO;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Bus;
import com.example.Online.Bus.Ticket.Booking.Application.repository.BusRepository;
import com.example.Online.Bus.Ticket.Booking.Application.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public List<BusDTO> getAllBuses() {
        return busRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<BusDTO> getBusById(Long id) {
        return busRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public BusDTO addBus(BusDTO busDTO) {
        Bus bus = mapToEntity(busDTO);
        busRepository.save(bus);
        return mapToDTO(bus);
    }

    @Override
    public BusDTO updateBus(BusDTO busDTO) {
        Optional<Bus> optBus = busRepository.findById(busDTO.getId());
        if (optBus.isPresent()) {
            Bus bus = optBus.get();
            bus.setBusNumber(busDTO.getBusNumber());
            bus.setBusType(busDTO.getBusType());
            bus.setSource(busDTO.getSource());
            bus.setDestination(busDTO.getDestination());
            bus.setDepartureTime(busDTO.getDepartureTime());
            bus.setArrivalTime(busDTO.getArrivalTime());
            bus.setAvailableSeats(busDTO.getAvailableSeats());
            bus.setFare(busDTO.getFare());
            busRepository.save(bus);
            return mapToDTO(bus);
        }
        return null;
    }

    @Override
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    @Override
    public List<BusDTO> findBusesByRoute(String source, String destination) {
        return busRepository.findBySourceAndDestination(source, destination)
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BusDTO> findAvailableBuses(String source, String destination, String date) {
        // For simplicity, this example reuses findBusesByRoute.
        // Date filtering logic can be added as needed.
        return findBusesByRoute(source, destination);
    }

    @Override
    public void updateSeatAvailability(Long busId, int seatsBooked) {
        Optional<Bus> optBus = busRepository.findById(busId);
        if (optBus.isPresent()) {
            Bus bus = optBus.get();
            int updatedSeats = bus.getAvailableSeats() - seatsBooked;
            bus.setAvailableSeats(updatedSeats);
            busRepository.save(bus);
        }
    }

    // Helper methods
    private BusDTO mapToDTO(Bus bus) {
        BusDTO dto = new BusDTO();
        dto.setId(bus.getId());
        dto.setBusNumber(bus.getBusNumber());
        dto.setBusType(bus.getBusType());
        dto.setSource(bus.getSource());
        dto.setDestination(bus.getDestination());
        dto.setDepartureTime(bus.getDepartureTime());
        dto.setArrivalTime(bus.getArrivalTime());
        dto.setAvailableSeats(bus.getAvailableSeats());
        dto.setFare(bus.getFare());
        return dto;
    }

    private Bus mapToEntity(BusDTO dto) {
        Bus bus = new Bus();
        bus.setId(dto.getId());
        bus.setBusNumber(dto.getBusNumber());
        bus.setBusType(dto.getBusType());
        bus.setSource(dto.getSource());
        bus.setDestination(dto.getDestination());
        bus.setDepartureTime(dto.getDepartureTime());
        bus.setArrivalTime(dto.getArrivalTime());
        bus.setAvailableSeats(dto.getAvailableSeats());
        bus.setFare(dto.getFare());
        return bus;
    }
}