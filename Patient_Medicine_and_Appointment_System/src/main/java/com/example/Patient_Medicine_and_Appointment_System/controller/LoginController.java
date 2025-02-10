package com.example.Patient_Medicine_and_Appointment_System.controller;

import ch.qos.logback.classic.Logger;
import com.example.Patient_Medicine_and_Appointment_System.model.User;
import com.example.Patient_Medicine_and_Appointment_System.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/")
    public String index(){
        return "patient-management";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, RedirectAttributes redirectAttributes, HttpSession httpSession){
        User currentUser = (User) httpSession.getAttribute("currentUser");
        if(Objects.nonNull(currentUser)){
            model.addAttribute("user",currentUser);
            return "dashboard";
        }
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        // Input validation
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Email and password must not be empty.");
            return "redirect:/login";
        }

        // Validate user
        User validUser = userService.validateUser(user.getEmail(), user.getPassword());
        if (Objects.nonNull(validUser)) {
            redirectAttributes.addFlashAttribute("message", "Login Successful!!");
            httpSession.setAttribute("currentUser", validUser);
            return "dashboard"; // Redirect to the dashboard URL
        } else {
            logger.warn("Failed login attempt for email: " + user.getEmail());
            redirectAttributes.addFlashAttribute("error", "Invalid email or password. Please try again");
            return "redirect:/login";
        }
    }
    @GetMapping("/dashboard")
    public String showDashboardPage(Model model, RedirectAttributes redirectAttributes, HttpSession httpSession){
        User currentUser = (User) httpSession.getAttribute("currentUser");
        if(Objects.nonNull(currentUser)){
            redirectAttributes.addFlashAttribute("error","You're not Logged In. Please re-login to again");
            return "redirect:/login";
        }
        model.addAttribute("user",currentUser);
        return "dashboard";
    }

}
