package com.example.demo.controller

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminController {

    @GetMapping("/admin/dashboard")
    fun adminDashboard(model: Model, auth: Authentication): String {
        model.addAttribute("username", auth.name)
        return "admin_dashboard" // возвращает страницу с кнопками таблиц
    }
}
