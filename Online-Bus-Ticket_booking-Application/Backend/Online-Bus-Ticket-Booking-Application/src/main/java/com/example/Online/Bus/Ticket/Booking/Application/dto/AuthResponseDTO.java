package com.example.Online.Bus.Ticket.Booking.Application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String message;
    private String name;
}

