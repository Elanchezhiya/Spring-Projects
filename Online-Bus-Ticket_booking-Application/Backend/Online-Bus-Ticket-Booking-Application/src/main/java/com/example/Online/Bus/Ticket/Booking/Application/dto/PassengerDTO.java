package com.example.Online.Bus.Ticket.Booking.Application.dto;

import com.example.Online.Bus.Ticket.Booking.Application.entity.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerDTO {
    private Long id;
    private String name;
    private int age;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private Long bookingId;
}
