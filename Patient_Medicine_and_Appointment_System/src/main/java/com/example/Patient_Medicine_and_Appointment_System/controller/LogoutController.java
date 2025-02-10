package com.example.Patient_Medicine_and_Appointment_System.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session= request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
