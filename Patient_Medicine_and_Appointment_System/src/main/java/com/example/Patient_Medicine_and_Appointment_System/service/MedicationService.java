package com.example.Patient_Medicine_and_Appointment_System.service;

import com.example.Patient_Medicine_and_Appointment_System.model.Medication;

import java.util.List;

public interface MedicationService {

    public List<Medication> getAllMedications();

    public Medication getMedicationById(Long id);

    public Medication saveMedication(Medication medication);

    public void deleteMedication(Long id);
}
