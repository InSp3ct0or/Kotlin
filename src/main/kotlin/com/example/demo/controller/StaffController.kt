package com.example.demo.controller

import com.example.demo.repository.StaffRepository
import com.example.demo.entity.Staff
import jakarta.persistence.EntityManager
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import jakarta.persistence.PersistenceContext

@Controller
@RequestMapping("/staff")
class StaffController(
    private val staffRepository: StaffRepository
) {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    // Просмотр списка сотрудников - доступно только воркерам
    @PreAuthorize("hasRole('ROLE_WORKER')")
    @GetMapping
    fun listStaff(model: Model): String {
        val staffList = staffRepository.findAll()
        model.addAttribute("staffList", staffList)
        return "staff_list"  // Шаблон для отображения списка сотрудников
    }

    // Отображение подробной информации о выбранном сотруднике
    @PreAuthorize("hasRole('ROLE_WORKER')")
    @GetMapping("/details/{staffId}")
    fun showStaffDetails(@PathVariable staffId: Long, model: Model): String {
        val staff = staffRepository.findById(staffId).orElseThrow { IllegalArgumentException("Сотрудник не найден") }

        // Расчет зарплаты для выбранного сотрудника
        val salaryResult = calculateSalary(staffId)

        // Отправка данных в модель
        model.addAttribute("staff", staff)
        model.addAttribute("salaryResult", salaryResult)

        return "staff_details"  // Шаблон для отображения данных сотрудника
    }

    // Вызов функции для расчета зарплаты
    private fun calculateSalary(staffId: Long): String {
        val query = entityManager.createNativeQuery("SELECT ST69589.calculate_salary(:staffId) FROM DUAL")
        query.setParameter("staffId", staffId)
        return query.singleResult as String
    }
}
