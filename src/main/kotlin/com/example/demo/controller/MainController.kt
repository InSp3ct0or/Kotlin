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

        if (auth != null) {
            model.addAttribute("username", auth.name)


            val isWorker = auth.authorities.contains(SimpleGrantedAuthority("ROLE_WORKER"))
            val isAdmin = auth.authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN"))


            model.addAttribute("isWorker", isWorker || isAdmin)
            model.addAttribute("isAdmin", isAdmin)
        } else {
            model.addAttribute("username", "Host")
            model.addAttribute("isWorker", false)
            model.addAttribute("isAdmin", false)
        }

        return "menu"
    }
}
