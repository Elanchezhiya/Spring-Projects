package com.example.Patient_Medicine_and_Appointment_System.repository;

import com.example.Patient_Medicine_and_Appointment_System.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
