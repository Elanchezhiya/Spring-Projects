package com.example.Patient_Medicine_and_Appointment_System.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;

    // Getters and Setters
}
