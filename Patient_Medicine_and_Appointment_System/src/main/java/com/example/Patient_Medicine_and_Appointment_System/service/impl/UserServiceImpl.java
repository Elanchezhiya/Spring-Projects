package com.example.Patient_Medicine_and_Appointment_System.service.impl;

import com.example.Patient_Medicine_and_Appointment_System.model.User;
import com.example.Patient_Medicine_and_Appointment_System.repository.UserRepository;
import com.example.Patient_Medicine_and_Appointment_System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

//    @Override
//    public User findAll(User user) {
//        return userRepository.findAll(user);
//    }

    @Override
    public User validateUser(String email,String password){
        return userRepository.findByEmailAndPassword(email, password);
    }

   }
