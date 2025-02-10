package com.example.Patient_Medicine_and_Appointment_System.repository;

import com.example.Patient_Medicine_and_Appointment_System.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
}
