package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ModelAttribute

@Controller
class RegistrationController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(@ModelAttribute user: User, model: Model): String {
        // Kontrola unikátního uživatele
        val existingUser = userRepository.findByUsername(user.username)
        if (existingUser != null) {
            model.addAttribute("error", "Uživatel s tímto jménem již existuje.")
            return "register"  // Vracíme formulář s chybou
        }

        // Hashování hesla a uložení uživatele
        val newUser = User(
            username = user.username,
            password = passwordEncoder.encode(user.password),  // Hashování hesla
            role = "ROLE_USER" // Výchozí role
        )
        userRepository.save(newUser)

        // Úspěšná registrace
        model.addAttribute("message", "Registrace byla úspěšná! Nyní se můžete přihlásit.")
        return "registration_success"  // Přesměrování na stránku úspěšné registrace
    }
}
