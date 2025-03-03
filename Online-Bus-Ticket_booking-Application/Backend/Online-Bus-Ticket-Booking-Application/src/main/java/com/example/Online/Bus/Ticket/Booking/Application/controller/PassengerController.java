package com.example.Online.Bus.Ticket.Booking.Application.controller;

import com.example.Online.Bus.Ticket.Booking.Application.dto.PassengerDTO;
import com.example.Online.Bus.Ticket.Booking.Application.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@Tag(name = "Passenger", description = "Passenger Management APIs")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @Operation(summary = "Add a new passenger")
    @PostMapping
    public PassengerDTO addPassenger(@RequestBody PassengerDTO passengerDTO) {
        return passengerService.addPassenger(passengerDTO);
    }

//    @Operation(summary = "Get passengers by booking ID")
//    @GetMapping("/booking/{bookingId}")
//    public List<PassengerDTO> getPassengersByBookingId(@PathVariable Long bookingId) {
//        return passengerService.getPassengersByBookingId(bookingId);
//    }

    @Operation(summary = "Delete a passenger")
    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
    }

    @Operation(summary = "Update a passenger")
    @PutMapping("/{id}")
    public PassengerDTO updatePassenger(@PathVariable Long id, @RequestBody PassengerDTO passengerDTO) {
        return passengerService.updatePassenger(id, passengerDTO);
    }

    @Operation(summary = "Get all passengers")
    @GetMapping("/all")
    public List<PassengerDTO> getAllPassengers() {
        return passengerService.getAllPassengers();
    }
}


