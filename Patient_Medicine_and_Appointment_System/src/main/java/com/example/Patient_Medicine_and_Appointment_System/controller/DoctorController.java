package com.example.Patient_Medicine_and_Appointment_System.controller;
import com.example.Patient_Medicine_and_Appointment_System.model.Appointment;
import com.example.Patient_Medicine_and_Appointment_System.model.Doctor;
import com.example.Patient_Medicine_and_Appointment_System.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // List all doctors
    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors"; // doctors.html
    }

    // View details of a single doctor
    @GetMapping("/{id}")
    public String viewDoctor(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id);
        model.addAttribute("doctor", doctor);
        return "doctor-detail"; // Create this template if needed
    }
    @GetMapping("/new")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "add-doctor";
    }

    // Save a new doctor (if you use a form for adding)
    @PostMapping
    public String addDoctor(@ModelAttribute Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors";
    }

    // Delete a doctor
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
