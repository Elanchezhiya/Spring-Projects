package com.example.Online.Bus.Ticket.Booking.Application.dto;
import com.example.Online.Bus.Ticket.Booking.Application.entity.BusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusDTO {
    private Long id;
    private String busNumber;
    private BusType busType;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int availableSeats;
    private double fare;
}
