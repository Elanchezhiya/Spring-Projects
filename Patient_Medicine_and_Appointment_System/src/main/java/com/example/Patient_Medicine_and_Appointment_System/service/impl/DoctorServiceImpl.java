package com.example.Patient_Medicine_and_Appointment_System.service.impl;

import com.example.Patient_Medicine_and_Appointment_System.model.Doctor;
import com.example.Patient_Medicine_and_Appointment_System.repository.DoctorRepository;
import com.example.Patient_Medicine_and_Appointment_System.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }
    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
