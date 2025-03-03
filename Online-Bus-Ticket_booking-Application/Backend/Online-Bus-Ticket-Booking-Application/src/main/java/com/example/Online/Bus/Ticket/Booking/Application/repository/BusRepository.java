package com.example.Online.Bus.Ticket.Booking.Application.repository;


import com.example.Online.Bus.Ticket.Booking.Application.entity.Bus;
import com.example.Online.Bus.Ticket.Booking.Application.entity.BusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findBySourceAndDestination(String source, String destination);
}

