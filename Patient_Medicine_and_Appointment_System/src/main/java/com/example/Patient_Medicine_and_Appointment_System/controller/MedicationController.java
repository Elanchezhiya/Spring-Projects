package com.example.Patient_Medicine_and_Appointment_System.controller;

import com.example.Patient_Medicine_and_Appointment_System.model.Appointment;
import com.example.Patient_Medicine_and_Appointment_System.model.Medication;
import com.example.Patient_Medicine_and_Appointment_System.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medications")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    // List all medications
    @GetMapping
    public String listMedications(Model model) {
        model.addAttribute("medications", medicationService.getAllMedications());
        return "medications"; // medications.html
    }

    @GetMapping("/new")
    public String showAppointmentForm(Model model) {
        model.addAttribute("medication", new Medication());
        return "add-medications"; // Thymeleaf template name
    }
    // Process form submission to create a new medication
    @PostMapping
    public String createMedication(@ModelAttribute Medication medication) {
        medicationService.saveMedication(medication);
        return "redirect:/medications";
    }

    // Show form for editing an existing medication
    @GetMapping("/edit/{id}")
    public String showEditMedicationForm(@PathVariable Long id, Model model) {
        Medication medication = medicationService.getMedicationById(id);
        if (medication == null) {
            return "redirect:/medications";
        }
        model.addAttribute("medication", medication);
        return "edit-medication"; // edit-medication.html
    }

    // Process form submission to update an existing medication
    @PostMapping("/edit/{id}")
    public String updateMedication(@PathVariable Long id, @ModelAttribute Medication medication) {
        Medication existing = medicationService.getMedicationById(id);
        if (existing == null) {
            return "redirect:/medications";
        }
        existing.setName(medication.getName());
        existing.setQuantity(medication.getQuantity());
        medicationService.saveMedication(existing);
        return "redirect:/medications";
    }

    // Delete a medication
    @GetMapping("/delete/{id}")
    public String deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return "redirect:/medications";
    }

    // View details of a medication (optional)
    @GetMapping("/{id}")
    public String viewMedication(@PathVariable Long id, Model model) {
        Medication medication = medicationService.getMedicationById(id);
        if (medication == null) {
            return "redirect:/medications";
        }
        model.addAttribute("medication", medication);
        return "medication-detail"; // medication-detail.html
    }
}
