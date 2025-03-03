package com.example.Online.Bus.Ticket.Booking.Application.controller;

import com.example.Online.Bus.Ticket.Booking.Application.dto.BookingDTO;
import com.example.Online.Bus.Ticket.Booking.Application.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking", description = "Booking Management APIs")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Create a new booking")
    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO) {
        return bookingService.createBooking(bookingDTO);
    }

    @Operation(summary = "Get all bookings")
    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @Operation(summary = "Cancel a booking")
    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }

//    @Operation(summary = "Get bookings by user ID")
//    @GetMapping("/user/{userId}")
//    public List<BookingDTO> getBookingsByUser(@PathVariable Long userId) {
//        return bookingService.getBookingsByUser(userId);
//    }

    @Operation(summary = "Confirm a booking")
    @PostMapping("/confirm/{bookingId}")
    public BookingDTO confirmBooking(@PathVariable Long bookingId) {
        return bookingService.confirmBooking(bookingId);
    }

    @Operation(summary = "Calculate total fare")
    @GetMapping("/fare/{busId}/{numberOfSeats}")
    public ResponseEntity<Double> calculateTotalFare(@PathVariable Long busId, @PathVariable int numberOfSeats) {
        double totalFare = bookingService.calculateTotalFare(busId, numberOfSeats);
        return ResponseEntity.ok(totalFare);
    }
}

