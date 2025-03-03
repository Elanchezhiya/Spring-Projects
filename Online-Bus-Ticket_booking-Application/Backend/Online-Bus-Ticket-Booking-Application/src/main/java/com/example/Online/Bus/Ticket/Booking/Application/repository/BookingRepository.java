package com.example.Online.Bus.Ticket.Booking.Application.repository;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Booking;
import com.example.Online.Bus.Ticket.Booking.Application.entity.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
//    List<Booking> findByUserId(Long userId);
}
