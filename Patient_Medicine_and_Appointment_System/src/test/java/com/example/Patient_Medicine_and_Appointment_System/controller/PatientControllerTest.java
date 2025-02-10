package com.example.Patient_Medicine_and_Appointment_System.controller;

import com.example.Patient_Medicine_and_Appointment_System.model.Patient;
import com.example.Patient_Medicine_and_Appointment_System.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Test
    public void testGetAllPatients() throws Exception {
        Mockito.when(patientService.getAllPatients()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    public void testGetPatientById() throws Exception {
        Patient patient = new Patient(1L, "Chezhi", "chezhiya1998@gmail.com", "Severe fever");
        Mockito.when(patientService.getPatientById(1L)).thenReturn(patient);
        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient-details"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    public void testSavePatient() throws Exception {
        mockMvc.perform(post("/patients")
                        .param("name", "New Patient")
                        .param("email", "newpatient@example.com")
                        .param("medicalHistory", "None"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
        Mockito.verify(patientService).savePatient(Mockito.any(Patient.class));
    }

    @Test
    public void testDeletePatient() throws Exception {
        mockMvc.perform(get("/patients/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
        Mockito.verify(patientService).deletePatient(1L);
    }
}
