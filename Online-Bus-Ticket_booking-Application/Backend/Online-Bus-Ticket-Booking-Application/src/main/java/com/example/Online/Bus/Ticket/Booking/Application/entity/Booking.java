package com.example.Online.Bus.Ticket.Booking.Application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

      // Relationship with Bus
    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @NotBlank
    private String bookingDate; // e.g. 2025-03-15

    @NotNull
    private int numberOfSeats;

    @NotNull
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentStatus paymentStatus;

    // Example seat selection: store seat numbers as CSV or separate entity
    private String selectedSeats; // e.g. "4,5,6"

   }
