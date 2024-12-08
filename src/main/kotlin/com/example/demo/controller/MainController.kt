package com.example.demo.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {

    @GetMapping("/")
    fun mainMenu(model: Model, auth: Authentication?): String {
        // Pokud je uživatel přihlášen, přidáme jeho jméno
        if (auth != null) {
            model.addAttribute("username", auth.name)

            // Kontrola role
            val isWorker = auth.authorities.contains(SimpleGrantedAuthority("ROLE_WORKER"))
            model.addAttribute("isWorker", isWorker)
        } else {
            model.addAttribute("username", "Host")
            model.addAttribute("isWorker", false)
        }

        return "menu" // Vrátí stránku menu.html
    }
}
