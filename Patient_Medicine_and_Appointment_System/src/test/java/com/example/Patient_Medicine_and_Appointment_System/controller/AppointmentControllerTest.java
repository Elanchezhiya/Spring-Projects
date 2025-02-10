package com.example.Patient_Medicine_and_Appointment_System.controller;

import com.example.Patient_Medicine_and_Appointment_System.model.Appointment;
import com.example.Patient_Medicine_and_Appointment_System.model.Doctor;
import com.example.Patient_Medicine_and_Appointment_System.model.Patient;
import com.example.Patient_Medicine_and_Appointment_System.service.AppointmentService;
import com.example.Patient_Medicine_and_Appointment_System.service.DoctorService;
import com.example.Patient_Medicine_and_Appointment_System.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AppointmentService appointmentService;
    @MockitoBean
    private PatientService patientService;
    @MockitoBean
    private DoctorService doctorService;

    @Test
    public void testListAppointments() throws Exception {
        Mockito.when(appointmentService.getAllAppointments()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/appointments"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments"))
                .andExpect(model().attributeExists("appointments"));
    }

    @Test
    public void testShowCreateAppointmentForm() throws Exception {
        Mockito.when(patientService.getAllPatients()).thenReturn(Collections.emptyList());
        Mockito.when(doctorService.getAllDoctors()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/appointments/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-appointments"))
                .andExpect(model().attributeExists("appointmentDTO"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("doctors"));
    }

    @Test
    public void testCreateAppointment() throws Exception {
        String dateTime = "2025-01-01T10:00";
        mockMvc.perform(post("/appointments")
                        .param("dateTime", dateTime)
                        .param("patientId", "1")
                        .param("doctorId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appointments"));
        verify(appointmentService).saveAppointment(any(Appointment.class));
    }

    @Test
    public void testShowEditAppointmentForm() throws Exception {
        Patient patient = new Patient(1L, "John Doe", "john@example.com", "None");
        Doctor doctor = new Doctor(2L, "Dr. Smith", "Cardiology");
        Appointment appointment = new Appointment(100L, LocalDateTime.of(2025, 1, 1, 10, 0), patient, doctor);
        Mockito.when(appointmentService.getAppointmentById(100L)).thenReturn(appointment);
        Mockito.when(patientService.getAllPatients()).thenReturn(Collections.singletonList(patient));
        Mockito.when(doctorService.getAllDoctors()).thenReturn(Collections.singletonList(doctor));
        mockMvc.perform(get("/appointments/edit/100"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-appointment"))
                .andExpect(model().attributeExists("appointmentDTO"))
                .andExpect(model().attribute("appointmentId", 100L));
    }

    @Test
    public void testUpdateAppointment() throws Exception {
        Patient patient = new Patient(1L, "John Doe", "john@example.com", "None");
        Doctor doctor = new Doctor(2L, "Dr. Smith", "Cardiology");
        Appointment appointment = new Appointment(100L, LocalDateTime.of(2025, 1, 1, 10, 0), patient, doctor);
        Mockito.when(appointmentService.getAppointmentById(100L)).thenReturn(appointment);
        mockMvc.perform(post("/appointments/edit/100")
                        .param("dateTime", "2025-01-01T12:00")
                        .param("patientId", "1")
                        .param("doctorId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appointments"));
        verify(appointmentService).saveAppointment(any(Appointment.class));
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        mockMvc.perform(get("/appointments/delete/100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appointments"));
        verify(appointmentService).deleteAppointment(100L);
    }

    @Test
    public void testViewAppointment() throws Exception {
        Patient patient = new Patient(1L, "John Doe", "john@example.com", "None");
        Doctor doctor = new Doctor(2L, "Dr. Smith", "Cardiology");
        Appointment appointment = new Appointment(100L, LocalDateTime.of(2025, 1, 1, 10, 0), patient, doctor);
        Mockito.when(appointmentService.getAppointmentById(100L)).thenReturn(appointment);
        mockMvc.perform(get("/appointments/100"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointment-detail"))
                .andExpect(model().attributeExists("appointment"));
    }
}
