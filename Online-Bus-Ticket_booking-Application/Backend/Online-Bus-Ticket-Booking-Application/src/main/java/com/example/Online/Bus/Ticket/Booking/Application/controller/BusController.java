package com.example.Online.Bus.Ticket.Booking.Application.controller;

import com.example.Online.Bus.Ticket.Booking.Application.dto.BusDTO;
import com.example.Online.Bus.Ticket.Booking.Application.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;

@RestController
@RequestMapping("/api/buses")
@Tag(name = "Bus", description = "Bus Management APIs")
public class BusController {
    @Autowired
    private BusService busService;

    @Operation(summary = "Get all buses")
    @GetMapping
    public List<BusDTO> getAllBuses() {
        return busService.getAllBuses();
    }

    @Operation(summary = "Get bus by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> getBusById(@PathVariable Long id) {
        Optional<BusDTO> busOpt = busService.getBusById(id);
        return busOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new bus")
    @PostMapping
    public BusDTO addBus(@RequestBody BusDTO busDTO) {
        return busService.addBus(busDTO);
    }

    @Operation(summary = "Update bus details")
    @PutMapping("/{id}")
    public BusDTO updateBus(@PathVariable Long id, @RequestBody BusDTO busDTO) {
        busDTO.setId(id);
        return busService.updateBus(busDTO);
    }

    @Operation(summary = "Delete a bus")
    @DeleteMapping("/{id}")
    public void deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
    }

    @Operation(summary = "Find buses by route")
    @GetMapping("/search/{source}/{destination}")
    public List<BusDTO> findBusesByRoute(@PathVariable String source, @PathVariable String destination) {
        return busService.findBusesByRoute(source, destination);
    }

    @Operation(summary = "Find available buses by route and date")
    @GetMapping("/available/{source}/{destination}/{date}")
    public List<BusDTO> findAvailableBuses(@PathVariable String source, @PathVariable String destination, @PathVariable String date) {
        return busService.findAvailableBuses(source, destination, date);
    }

    @Operation(summary = "Update seat availability")
    @PostMapping("/{id}/update-seats")
    public ResponseEntity<String> updateSeatAvailability(@PathVariable Long id, @RequestParam int seatsBooked) {
        busService.updateSeatAvailability(id, seatsBooked);
        return ResponseEntity.ok("Seat availability updated.");
    }
}

