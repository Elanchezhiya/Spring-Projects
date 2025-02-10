package com.example.Patient_Medicine_and_Appointment_System.service.impl;

import com.example.Patient_Medicine_and_Appointment_System.model.Appointment;
import com.example.Patient_Medicine_and_Appointment_System.repository.AppointmentRepository;
import com.example.Patient_Medicine_and_Appointment_System.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }
    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
