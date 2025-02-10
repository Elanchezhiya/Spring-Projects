package com.example.Patient_Medicine_and_Appointment_System.service;

import com.example.Patient_Medicine_and_Appointment_System.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
    public Patient getPatientById(Long id);
    public Patient savePatient(Patient patient);
    public void deletePatient(Long id);
}
