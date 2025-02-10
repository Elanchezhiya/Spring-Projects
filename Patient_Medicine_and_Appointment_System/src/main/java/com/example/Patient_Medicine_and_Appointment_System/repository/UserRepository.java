package com.example.Patient_Medicine_and_Appointment_System.repository;

import com.example.Patient_Medicine_and_Appointment_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

//    User findAll(User user);
}
