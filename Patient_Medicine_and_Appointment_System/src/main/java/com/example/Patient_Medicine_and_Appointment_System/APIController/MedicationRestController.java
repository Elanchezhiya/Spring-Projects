package com.example.Patient_Medicine_and_Appointment_System.APIController;

import com.example.Patient_Medicine_and_Appointment_System.model.Medication;
import com.example.Patient_Medicine_and_Appointment_System.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
public class MedicationRestController {

    @Autowired
    private MedicationRepository medicationRepository;

    // Get all medications
    @GetMapping
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    // Get medication by ID
    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable Long id) {
        return medicationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new medication
    @PostMapping
    public Medication createMedication(@RequestBody Medication medication) {
        return medicationRepository.save(medication);
    }

    // Update an existing medication
    @PutMapping("/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable Long id, @RequestBody Medication medicationDetails) {
        return medicationRepository.findById(id).map(medication -> {
            medication.setName(medicationDetails.getName());
            medication.setQuantity(medicationDetails.getQuantity());
            return ResponseEntity.ok(medicationRepository.save(medication));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete a medication
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMedication(@PathVariable Long id) {
        return medicationRepository.findById(id).map(medication -> {
            medicationRepository.delete(medication);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
