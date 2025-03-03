package com.example.Online.Bus.Ticket.Booking.Application.service.serviceImpl;

import com.example.Online.Bus.Ticket.Booking.Application.dto.PassengerDTO;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Booking;
import com.example.Online.Bus.Ticket.Booking.Application.entity.Passenger;
import com.example.Online.Bus.Ticket.Booking.Application.repository.BookingRepository;
import com.example.Online.Bus.Ticket.Booking.Application.repository.PassengerRepository;
import com.example.Online.Bus.Ticket.Booking.Application.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public PassengerDTO addPassenger(PassengerDTO passengerDTO) {
        Passenger passenger = new Passenger();
        passenger.setName(passengerDTO.getName());
        passenger.setAge(passengerDTO.getAge());
        passenger.setGender(passengerDTO.getGender());
        passenger.setEmail(passengerDTO.getEmail());
        passenger.setPhoneNumber(passengerDTO.getPhoneNumber());

//        Booking booking = bookingRepository.findById(passengerDTO.getBookingId())
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//        passenger.setBooking(booking);

        passengerRepository.save(passenger);
        passengerDTO.setId(passenger.getId());
        return passengerDTO;
    }

//    @Override
//    public List<PassengerDTO> getPassengersByBookingId(Long bookingId) {
//        return passengerRepository.findByBookingId(bookingId).stream().map(p -> {
//            PassengerDTO dto = new PassengerDTO();
//            dto.setId(p.getId());
//            dto.setName(p.getName());
//            dto.setAge(p.getAge());
//            dto.setGender(p.getGender());
//            dto.setEmail(p.getEmail());
//            dto.setPhoneNumber(p.getPhoneNumber());
//            dto.setBookingId(bookingId);
//            return dto;
//        }).collect(Collectors.toList());
//    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public PassengerDTO updatePassenger(Long passengerId, PassengerDTO passengerDTO) {
        Optional<Passenger> optPassenger = passengerRepository.findById(passengerId);
        if (optPassenger.isPresent()) {
            Passenger passenger = optPassenger.get();
            passenger.setName(passengerDTO.getName());
            passenger.setAge(passengerDTO.getAge());
            passenger.setGender(passengerDTO.getGender());
            passenger.setEmail(passengerDTO.getEmail());
            passenger.setPhoneNumber(passengerDTO.getPhoneNumber());
            passengerRepository.save(passenger);
            passengerDTO.setId(passenger.getId());
            return passengerDTO;
        }
        return null;
    }

    @Override
    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll().stream().map(p -> {
            PassengerDTO dto = new PassengerDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setAge(p.getAge());
            dto.setGender(p.getGender());
            dto.setEmail(p.getEmail());
            dto.setPhoneNumber(p.getPhoneNumber());
           // dto.setBookingId(p.getBooking().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}


