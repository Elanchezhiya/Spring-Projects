package com.example.Online.Bus.Ticket.Booking.Application.dto;

import com.example.Online.Bus.Ticket.Booking.Application.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private Set<Role> roles;
}

