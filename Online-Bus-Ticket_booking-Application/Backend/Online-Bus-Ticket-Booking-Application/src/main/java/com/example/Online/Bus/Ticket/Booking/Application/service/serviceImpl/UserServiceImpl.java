package com.example.Online.Bus.Ticket.Booking.Application.service.serviceImpl;

import com.example.Online.Bus.Ticket.Booking.Application.dto.UserDTO;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Role;
import com.example.Online.Bus.Ticket.Booking.Application.entity.User;
import com.example.Online.Bus.Ticket.Booking.Application.repository.UserRepository;
import com.example.Online.Bus.Ticket.Booking.Application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhoneNumber(userDTO.getPhoneNumber());

        // Assign roles (ADMIN for specific emails, otherwise USER)
        if ("admin@gmail.com".equalsIgnoreCase(userDTO.getEmail()) ||
                "admin".equalsIgnoreCase(userDTO.getUsername())) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        } else {
            user.setRoles(userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()
                    ? userDTO.getRoles()
                    : Collections.singleton(Role.USER));
        }

        user = userRepository.save(user);
        return mapToDTO(user);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            user.setRoles(userDTO.getRoles());
        }

        user = userRepository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO updateUserByEmail(String email, UserDTO updatedUser) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(updatedUser.getUsername());
        user.setPhoneNumber(updatedUser.getPhoneNumber());

        user = userRepository.save(user);
        return mapToDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::mapToDTO);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // Helper method to map User -> UserDTO
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail()); // Email should not be changed
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRoles(user.getRoles());
        return dto;
    }
}

