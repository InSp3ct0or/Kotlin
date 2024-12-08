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
        // Если пользователь авторизован, добавляем его имя в модель
        if (auth != null) {
            model.addAttribute("username", auth.name)

            // Проверка роли
            val isWorker = auth.authorities.contains(SimpleGrantedAuthority("ROLE_WORKER"))
            model.addAttribute("isWorker", isWorker)
        } else {
            model.addAttribute("username", "Гость")
            model.addAttribute("isWorker", false)
        }

        return "menu" // Вернуть страницу menu.html
    }
}
