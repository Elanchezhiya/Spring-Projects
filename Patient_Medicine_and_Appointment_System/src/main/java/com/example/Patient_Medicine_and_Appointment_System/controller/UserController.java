package com.example.Patient_Medicine_and_Appointment_System.controller;

import com.example.Patient_Medicine_and_Appointment_System.model.User;
import com.example.Patient_Medicine_and_Appointment_System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        userService.save(user);
        redirectAttributes.addFlashAttribute("message","Registration successful!! Please Login.");
        return "redirect:/login";
    }
//    @GetMapping("/profile")
//    public String profilePage(Model model){
//        User user = (User) model.addAttribute("user",new User());
//        userService.findAll(user);
//        return "profile";
//    }
}
