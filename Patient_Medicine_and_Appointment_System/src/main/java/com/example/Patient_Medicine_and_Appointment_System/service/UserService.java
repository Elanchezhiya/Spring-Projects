package com.example.Patient_Medicine_and_Appointment_System.service;

import com.example.Patient_Medicine_and_Appointment_System.model.User;

public interface UserService {
    User save(User user);
//    User findAll(User user);
    User validateUser(String email,String password);
}
