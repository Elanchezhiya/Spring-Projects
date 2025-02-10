package com.example.Patient_Medicine_and_Appointment_System.service;

import com.example.Patient_Medicine_and_Appointment_System.model.Doctor;

import java.util.List;

public interface DoctorService {
    public List<Doctor> getAllDoctors();

    public Doctor getDoctorById(Long id);

    public Doctor saveDoctor(Doctor doctor);

    public void deleteDoctor(Long id);
}
