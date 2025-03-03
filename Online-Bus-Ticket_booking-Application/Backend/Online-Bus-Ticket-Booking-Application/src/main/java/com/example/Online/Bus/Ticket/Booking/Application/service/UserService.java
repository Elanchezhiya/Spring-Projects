package com.example.Online.Bus.Ticket.Booking.Application.service;

import com.example.Online.Bus.Ticket.Booking.Application.dto.UserDTO;
import java.util.List;
import java.util.Optional;


public interface UserService {
    // Basic CRUD operations
    UserDTO registerUser(UserDTO userDTO);
    Optional<UserDTO> getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    List<UserDTO> getAllUsers();

    // Additional methods
    Optional<UserDTO> getUserByEmail(String email);
    void changePassword(Long userId,String newPassword);

    UserDTO updateUserByEmail(String email, UserDTO userDTO);
}



