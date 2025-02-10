package com.example.Patient_Medicine_and_Appointment_System.controller;

import com.example.Patient_Medicine_and_Appointment_System.model.Doctor;
import com.example.Patient_Medicine_and_Appointment_System.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DoctorService doctorService;

    @Test
    public void testListDoctors() throws Exception {
        Mockito.when(doctorService.getAllDoctors()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctors"))
                .andExpect(model().attributeExists("doctors"));
    }

    @Test
    public void testViewDoctor() throws Exception {
        Doctor doctor = new Doctor(1L, "Dr. Smith", "Cardiology");
        Mockito.when(doctorService.getDoctorById(1L)).thenReturn(doctor);
        mockMvc.perform(get("/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor-detail"))
                .andExpect(model().attributeExists("doctor"));
    }

    @Test
    public void testShowAddDoctorForm() throws Exception {
        mockMvc.perform(get("/doctors/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-doctor"))
                .andExpect(model().attributeExists("doctor"));
    }

    @Test
    public void testAddDoctor() throws Exception {
        mockMvc.perform(post("/doctors")
                        .param("name", "Dr. Adams")
                        .param("specialization", "Pediatrics"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctors"));
        Mockito.verify(doctorService).saveDoctor(Mockito.any(Doctor.class));
    }

    @Test
    public void testDeleteDoctor() throws Exception {
        mockMvc.perform(get("/doctors/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctors"));
        Mockito.verify(doctorService).deleteDoctor(1L);
    }
}