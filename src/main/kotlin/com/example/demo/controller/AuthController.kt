package com.example.demo.controller

import com.example.demo.entity.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ModelAttribute

@Controller
@RequestMapping("/auth")
class AuthController {

    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        model.addAttribute("user", User())
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(@ModelAttribute user: User, model: Model): String {
        // Registrace uživatele
        return "registration_success"
    }
}
