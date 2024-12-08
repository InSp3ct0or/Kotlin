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

    // Отображение страницы с выбором сотрудника для расчета зарплаты
    @GetMapping("/calculate-salary")
    fun showSalaryCalculationForm(model: Model): String {
        val staffList = staffRepository.findAll() // Получаем всех сотрудников
        model.addAttribute("staffList", staffList)
        return "salary_form" // Шаблон для выбора сотрудника
    }

    // Обработка расчета зарплаты по выбранному сотруднику
    @PostMapping("/calculate-salary")
    fun calculateSalary(
        @RequestParam staffId: Long,
        model: Model
    ): String {
        val salaryInfo = salaryService.calculateSalary(staffId) // Вычисление зарплаты
        model.addAttribute("salaryInfo", salaryInfo) // Добавляем результат в модель
        return "salary_result" // Шаблон для отображения результатов
    }
}
