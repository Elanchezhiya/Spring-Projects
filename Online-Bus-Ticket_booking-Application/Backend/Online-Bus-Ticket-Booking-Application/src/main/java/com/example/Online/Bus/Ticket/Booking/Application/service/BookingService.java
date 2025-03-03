package com.example.Online.Bus.Ticket.Booking.Application.service;

import com.example.Online.Bus.Ticket.Booking.Application.dto.BookingDTO;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Booking;
import com.example.Online.Bus.Ticket.Booking.Application.entity.PaymentStatus;
import org.springframework.data.domain.Page;

import java.util.List;

import java.util.List;

public interface BookingService {
    // Basic CRUD operations
    BookingDTO createBooking(BookingDTO bookingDTO);
    List<BookingDTO> getAllBookings();
    void cancelBooking(Long id);

    // Additional methods
   // List<BookingDTO> getBookingsByUser(Long userId);
    BookingDTO confirmBooking(Long bookingId);
    double calculateTotalFare(Long busId, int numberOfSeats);
}
