package com.example.Patient_Medicine_and_Appointment_System.controller;

import com.example.Patient_Medicine_and_Appointment_System.model.Medication;
import com.example.Patient_Medicine_and_Appointment_System.service.MedicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicationController.class)
public class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicationService medicationService;

    @Test
    public void testListMedications() throws Exception {
        Mockito.when(medicationService.getAllMedications()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/medications"))
                .andExpect(status().isOk())
                .andExpect(view().name("medications"))
                .andExpect(model().attributeExists("medications"));
    }

    @Test
    public void testShowNewMedicationForm() throws Exception {
        mockMvc.perform(get("/medications/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-medications"))
                .andExpect(model().attributeExists("medication"));
    }

    @Test
    public void testCreateMedication() throws Exception {
        mockMvc.perform(post("/medications")
                        .param("name", "Aspirin")
                        .param("quantity", "100"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/medications"));
        Mockito.verify(medicationService).saveMedication(Mockito.any(Medication.class));
    }

    @Test
    public void testShowEditMedicationForm() throws Exception {
        Medication medication = new Medication(1L, "Aspirin", 100);
        Mockito.when(medicationService.getMedicationById(1L)).thenReturn(medication);
        mockMvc.perform(get("/medications/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-medication"))
                .andExpect(model().attributeExists("medication"));
    }

    @Test
    public void testUpdateMedication() throws Exception {
        Medication medication = new Medication(1L, "Aspirin", 100);
        Mockito.when(medicationService.getMedicationById(1L)).thenReturn(medication);
        mockMvc.perform(post("/medications/edit/1")
                        .param("name", "Aspirin Updated")
                        .param("quantity", "150"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/medications"));
        Mockito.verify(medicationService).saveMedication(Mockito.any(Medication.class));
    }

    @Test
    public void testDeleteMedication() throws Exception {
        mockMvc.perform(get("/medications/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/medications"));
        Mockito.verify(medicationService).deleteMedication(1L);
    }

    @Test
    public void testViewMedication() throws Exception {
        Medication medication = new Medication(1L, "Aspirin", 100);
        Mockito.when(medicationService.getMedicationById(1L)).thenReturn(medication);
        mockMvc.perform(get("/medications/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("medication-detail"))
                .andExpect(model().attributeExists("medication"));
    }
}