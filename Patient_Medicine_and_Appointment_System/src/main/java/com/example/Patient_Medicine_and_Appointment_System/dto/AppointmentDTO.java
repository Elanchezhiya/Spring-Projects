package com.example.Patient_Medicine_and_Appointment_System.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private LocalDateTime dateTime;
    private Long patientId;
    private Long doctorId;
}
