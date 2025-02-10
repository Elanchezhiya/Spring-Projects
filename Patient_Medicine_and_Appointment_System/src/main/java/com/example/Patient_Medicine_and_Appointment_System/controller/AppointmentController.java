package com.example.Patient_Medicine_and_Appointment_System.controller;

import com.example.Patient_Medicine_and_Appointment_System.dto.AppointmentDTO;
import com.example.Patient_Medicine_and_Appointment_System.model.Appointment;
import com.example.Patient_Medicine_and_Appointment_System.model.Doctor;
import com.example.Patient_Medicine_and_Appointment_System.model.Patient;
import com.example.Patient_Medicine_and_Appointment_System.service.AppointmentService;
import com.example.Patient_Medicine_and_Appointment_System.service.DoctorService;
import com.example.Patient_Medicine_and_Appointment_System.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    // Display list of appointments (for example)
    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments"; // Your appointments listing page
    }

    // Show form for creating a new appointment
    @GetMapping("/new")
    public String showCreateAppointmentForm(Model model) {
        model.addAttribute("appointmentDTO", new AppointmentDTO());
        // Provide lists for the dropdowns
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "add-appointments";  // Thymeleaf template for creating an appointment
    }

    // Handle form submission to create a new appointment
    @PostMapping
    public String createAppointment(@ModelAttribute("appointmentDTO") AppointmentDTO appointmentDTO) {
        // Convert the DTO to an Appointment entity
        Appointment appointment = new Appointment();
        appointment.setDateTime(appointmentDTO.getDateTime());

        // Lookup the patient and doctor using their IDs
        Patient patient = patientService.getPatientById(appointmentDTO.getPatientId());
        Doctor doctor = doctorService.getDoctorById(appointmentDTO.getDoctorId());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        // Save the appointment
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    // Show form to edit an existing appointment
    @GetMapping("/edit/{id}")
    public String showEditAppointmentForm(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null) {
            return "redirect:/appointments";
        }
        // Convert the Appointment entity to AppointmentDTO
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDateTime(appointment.getDateTime());
        appointmentDTO.setPatientId(appointment.getPatient().getId());
        appointmentDTO.setDoctorId(appointment.getDoctor().getId());

        model.addAttribute("appointmentDTO", appointmentDTO);
        model.addAttribute("appointmentId", appointment.getId());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "edit-appointment";  // edit-appointment.html
    }

    // Process form submission to update an existing appointment
    @PostMapping("/edit/{id}")
    public String updateAppointment(@PathVariable Long id, @ModelAttribute("appointmentDTO") AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null) {
            return "redirect:/appointments";
        }
        appointment.setDateTime(appointmentDTO.getDateTime());
        Patient patient = patientService.getPatientById(appointmentDTO.getPatientId());
        Doctor doctor = doctorService.getDoctorById(appointmentDTO.getDoctorId());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    // Delete an appointment
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }

    // View details of an appointment (optional)
    @GetMapping("/{id}")
    public String viewAppointment(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null) {
            return "redirect:/appointments";
        }
        model.addAttribute("appointment", appointment);
        return "appointment-detail";  // appointment-detail.html
    }

}