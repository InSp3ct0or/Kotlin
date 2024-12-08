package com.example.demo.controller

import com.example.demo.repository.StaffRepository
import com.example.demo.service.SalaryService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class SalaryController(
    private val staffRepository: StaffRepository,
    private val salaryService: SalaryService
) {

    // Výběr zaměstnanců
    @GetMapping("/calculate-salary")
    fun showSalaryCalculationForm(model: Model): String {
        val staffList = staffRepository.findAll() // Získání seznamu zaměstnanců
        model.addAttribute("staffList", staffList)
        return "salary_form" // Formulář pro výběr zaměstnanců
    }

    // Výpočet mzdy
    @PostMapping("/calculate-salary")
    fun calculateSalary(
        @RequestParam staffId: Long,
        model: Model
    ): String {
        val salaryInfo = salaryService.calculateSalary(staffId) // Výpočet mzdy
        model.addAttribute("salaryInfo", salaryInfo) // Přidání výsledků
        return "salary_result" // Zobrazení výsledků
    }
}
