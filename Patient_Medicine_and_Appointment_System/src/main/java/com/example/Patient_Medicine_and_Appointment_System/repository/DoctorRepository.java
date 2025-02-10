package com.example.Patient_Medicine_and_Appointment_System.repository;

import com.example.Patient_Medicine_and_Appointment_System.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
