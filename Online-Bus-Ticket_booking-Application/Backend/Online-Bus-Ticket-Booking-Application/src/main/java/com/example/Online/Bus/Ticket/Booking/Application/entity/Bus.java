package com.example.Online.Bus.Ticket.Booking.Application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "buses")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String busNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BusType busType;

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @NotBlank
    private String departureTime;

    @NotBlank
    private String arrivalTime;

    @NotNull
    private int availableSeats;

    @NotNull
    private double fare;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
