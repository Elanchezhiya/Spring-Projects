package com.example.Patient_Medicine_and_Appointment_System.service.impl;
import com.example.Patient_Medicine_and_Appointment_System.model.Medication;
import com.example.Patient_Medicine_and_Appointment_System.repository.MedicationRepository;
import com.example.Patient_Medicine_and_Appointment_System.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;
    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }
    @Override
    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id).orElse(null);
    }
    @Override
    public Medication saveMedication(Medication medication) {
        return medicationRepository.save(medication);
    }
    @Override
    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }
}
