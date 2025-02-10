package com.example.Patient_Medicine_and_Appointment_System.repository;

import com.example.Patient_Medicine_and_Appointment_System.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}