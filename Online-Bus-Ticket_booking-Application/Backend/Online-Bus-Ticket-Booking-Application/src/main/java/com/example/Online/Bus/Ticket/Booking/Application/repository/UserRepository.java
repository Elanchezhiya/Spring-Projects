package com.example.Online.Bus.Ticket.Booking.Application.repository;

import com.example.Online.Bus.Ticket.Booking.Application.entity.Role;
import com.example.Online.Bus.Ticket.Booking.Application.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}

