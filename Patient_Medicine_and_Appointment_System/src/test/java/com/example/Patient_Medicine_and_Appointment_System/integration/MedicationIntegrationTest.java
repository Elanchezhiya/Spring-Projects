package com.example.Patient_Medicine_and_Appointment_System.integration;

import com.example.Patient_Medicine_and_Appointment_System.repository.MedicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateMedicationIntegration() throws Exception {
        // Using form URL encoding for a simple integration test
        mockMvc.perform(post("/medications")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "IntegrationMed")
                        .param("quantity", "200"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/medications"));
    }

    @Test
    public void testGetMedicationsPageIntegration() throws Exception {
        mockMvc.perform(get("/medications"))
                .andExpect(status().isOk())
                .andExpect(view().name("medications"))
                .andExpect(model().attributeExists("medications"));
    }
}