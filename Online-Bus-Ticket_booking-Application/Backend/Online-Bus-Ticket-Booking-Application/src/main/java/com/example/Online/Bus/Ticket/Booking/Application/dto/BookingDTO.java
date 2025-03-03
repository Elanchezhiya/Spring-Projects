package com.example.Online.Bus.Ticket.Booking.Application.dto;

import com.example.Online.Bus.Ticket.Booking.Application.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long busId;
    private String bookingDate;
    private int numberOfSeats;
    private double totalAmount;
    private PaymentStatus paymentStatus;
    private String selectedSeats; // e.g. \"4,5,6\"
}

