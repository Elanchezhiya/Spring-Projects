package com.example.Online.Bus.Ticket.Booking.Application.controller;

import com.example.Online.Bus.Ticket.Booking.Application.dto.ChangePasswordDTO;
import com.example.Online.Bus.Ticket.Booking.Application.dto.UserDTO;
import com.example.Online.Bus.Ticket.Booking.Application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User Management APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @Operation(summary = "Get current logged-in user profile")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUserProfile(Authentication authentication) {
        String email = authentication.getName(); // Get logged-in user email
        Optional<UserDTO> userOpt = userService.getUserByEmail(email);
        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user by ID (Admin Only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userOpt = userService.getUserById(id);
        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all users (Admin Only)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Update user details")
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        String email = authentication.getName(); // Get logged-in user's email
        return ResponseEntity.ok(userService.updateUserByEmail(email, userDTO));
    }

    @Operation(summary = "Delete a user (Admin Only)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @Operation(summary = "Change user password")
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, Authentication authentication) {
        String email = authentication.getName(); // Get logged-in user email
        userService.changePassword(Long.valueOf(email), changePasswordDTO.getNewPassword());
        return ResponseEntity.ok("Password changed successfully.");
    }
}




