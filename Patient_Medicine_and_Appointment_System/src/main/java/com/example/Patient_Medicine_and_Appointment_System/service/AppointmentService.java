package com.example.Patient_Medicine_and_Appointment_System.service;

import com.example.Patient_Medicine_and_Appointment_System.model.Appointment;

import java.util.List;

public interface AppointmentService {

    public List<Appointment> getAllAppointments();

    public Appointment getAppointmentById(Long id);

    public Appointment saveAppointment(Appointment appointment);

    public void deleteAppointment(Long id);

    }
