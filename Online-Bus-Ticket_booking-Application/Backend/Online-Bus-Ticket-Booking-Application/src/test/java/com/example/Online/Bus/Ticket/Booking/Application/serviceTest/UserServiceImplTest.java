package com.example.Online.Bus.Ticket.Booking.Application.serviceTest;

import com.example.Online.Bus.Ticket.Booking.Application.dto.UserDTO;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Role;
import com.example.Online.Bus.Ticket.Booking.Application.entity.User;
import com.example.Online.Bus.Ticket.Booking.Application.repository.UserRepository;
import com.example.Online.Bus.Ticket.Booking.Application.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Optional;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");
        userDTO.setPhoneNumber("9876543210");

        User user = new User();
        user.setId(1L);
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword("encodedPassword");
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRoles(Collections.singleton(Role.USER));

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.registerUser(userDTO);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    public void testGetUserById_Success() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    public void testUpdateUser_Success() {
        User user = new User();
        user.setId(1L);
        user.setUsername("oldName");
        user.setEmail("test@example.com");
        user.setPassword("oldPassword");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newName");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.updateUser(1L, userDTO);

        assertEquals("newName", result.getUsername());
    }

    @Test
    public void testChangePassword_Success() {
        User user = new User();
        user.setId(1L);
        user.setPassword("oldPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        userService.changePassword(1L, "newPassword");

        verify(userRepository, times(1)).save(user);
        assertEquals("encodedNewPassword", user.getPassword());
    }
}


