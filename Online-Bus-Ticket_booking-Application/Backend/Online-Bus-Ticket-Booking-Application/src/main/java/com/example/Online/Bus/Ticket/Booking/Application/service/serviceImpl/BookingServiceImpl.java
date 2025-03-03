package com.example.Online.Bus.Ticket.Booking.Application.service.serviceImpl;

import com.example.Online.Bus.Ticket.Booking.Application.dto.BookingDTO;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Booking;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Bus;
import com.example.Online.Bus.Ticket.Booking.Application.entity.PaymentStatus;
import com.example.Online.Bus.Ticket.Booking.Application.repository.BookingRepository;
import com.example.Online.Bus.Ticket.Booking.Application.repository.BusRepository;
import com.example.Online.Bus.Ticket.Booking.Application.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
//    @Autowired
//    private UserRepository userRepository;
    @Autowired
    private BusRepository busRepository;

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        //User user = userRepository.findById(bookingDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Bus bus = busRepository.findById(bookingDTO.getBusId()).orElseThrow(() -> new RuntimeException("Bus not found"));

        //booking.setUser(user);
        booking.setBus(bus);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setTotalAmount(bookingDTO.getTotalAmount());
        booking.setPaymentStatus(bookingDTO.getPaymentStatus());
        booking.setSelectedSeats(bookingDTO.getSelectedSeats());

        // Update available seats in bus
        int updatedSeats = bus.getAvailableSeats() - bookingDTO.getNumberOfSeats();
        bus.setAvailableSeats(updatedSeats);
        busRepository.save(bus);

        bookingRepository.save(booking);
        bookingDTO.setId(booking.getId());
        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(b -> {
            BookingDTO dto = new BookingDTO();
            dto.setId(b.getId());
           // dto.setUserId(b.getUser().getId());
            dto.setBusId(b.getBus().getId());
            dto.setBookingDate(b.getBookingDate());
            dto.setNumberOfSeats(b.getNumberOfSeats());
            dto.setTotalAmount(b.getTotalAmount());
            dto.setPaymentStatus(b.getPaymentStatus());
            dto.setSelectedSeats(b.getSelectedSeats());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long id) {
        bookingRepository.deleteById(id);
    }

//    @Override
//    public List<BookingDTO> getBookingsByUser(Long userId) {
//        return bookingRepository.findByUserId(userId).stream().map(b -> {
//            BookingDTO dto = new BookingDTO();
//            dto.setId(b.getId());
//           // dto.setUserId(b.getUser().getId());
//            dto.setBusId(b.getBus().getId());
//            dto.setBookingDate(b.getBookingDate());
//            dto.setNumberOfSeats(b.getNumberOfSeats());
//            dto.setTotalAmount(b.getTotalAmount());
//            dto.setPaymentStatus(b.getPaymentStatus());
//            dto.setSelectedSeats(b.getSelectedSeats());
//            return dto;
//        }).collect(Collectors.toList());
//    }

    @Override
    public BookingDTO confirmBooking(Long bookingId) {
        Optional<Booking> optBooking = bookingRepository.findById(bookingId);
        if (optBooking.isPresent()) {
            Booking booking = optBooking.get();
            // For example, set paymentStatus to COMPLETED
            booking.setPaymentStatus(PaymentStatus.COMPLETED);
            bookingRepository.save(booking);
            BookingDTO dto = new BookingDTO();
            dto.setId(booking.getId());
           // dto.setUserId(booking.getUser().getId());
            dto.setBusId(booking.getBus().getId());
            dto.setBookingDate(booking.getBookingDate());
            dto.setNumberOfSeats(booking.getNumberOfSeats());
            dto.setTotalAmount(booking.getTotalAmount());
            dto.setPaymentStatus(booking.getPaymentStatus());
            dto.setSelectedSeats(booking.getSelectedSeats());
            return dto;
        } else {
            throw new RuntimeException("Booking not found");
        }
    }

    @Override
    public double calculateTotalFare(Long busId, int numberOfSeats) {
        Bus bus = busRepository.findById(busId).orElseThrow(() -> new RuntimeException("Bus not found"));
        return bus.getFare() * numberOfSeats;
    }
}



