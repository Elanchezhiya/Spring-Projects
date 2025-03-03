package com.example.Online.Bus.Ticket.Booking.Application.controllerTest;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Online.Bus.Ticket.Booking.Application.controller.AuthController;
import com.example.Online.Bus.Ticket.Booking.Application.dto.UserDTO;
import com.example.Online.Bus.Ticket.Booking.Application.entity.User;
import com.example.Online.Bus.Ticket.Booking.Application.repository.UserRepository;
import com.example.Online.Bus.Ticket.Booking.Application.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegister_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");
        userDTO.setPhoneNumber("9876543210");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("hashedPassword");
        when(jwtUtil.generateToken(userDTO.getEmail())).thenReturn("mockedToken");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("mockedToken"))
                .andExpect(jsonPath("$.message").value("Registration successful"));

        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void testRegister_EmailAlreadyExists() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");
        userDTO.setPhoneNumber("9876543210");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already registered"));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLogin_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(null);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");
        userDTO.setPhoneNumber(null);
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("hashedPassword");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(userDTO.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(userDTO.getEmail())).thenReturn("mockedToken");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockedToken"))
                .andExpect(jsonPath("$.message").value("Login successful"));
    }

    @Test
    void testLogin_InvalidPassword() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(null);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("wrongPassword");
        userDTO.setPhoneNumber(null);
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("hashedPassword");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(userDTO.getPassword(), mockUser.getPassword())).thenReturn(false);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid credentials"));
    }

    @Test
    void testLogin_UserNotFound() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(null);
        userDTO.setEmail("unknown@example.com");
        userDTO.setPassword("password123");
        userDTO.setPhoneNumber(null);
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}

