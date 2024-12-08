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

    // Seznam zaměstnanců - pouze pro pracovníky
    @PreAuthorize("hasRole('ROLE_WORKER')")
    @GetMapping
    fun listStaff(model: Model): String {
        val staffList = staffRepository.findAll()
        model.addAttribute("staffList", staffList)
        return "staff_list"  // Šablona pro zobrazení seznamu zaměstnanců
    }

    // Zobrazení detailů zaměstnanců
    @PreAuthorize("hasRole('ROLE_WORKER')")
    @GetMapping("/details/{staffId}")
    fun showStaffDetails(@PathVariable staffId: Long, model: Model): String {
        val staff = staffRepository.findById(staffId).orElseThrow { IllegalArgumentException("Zaměstnanec nenalezen") }

        // Výpočet mzdy pro zaměstnanca
        val salaryResult = calculateSalary(staffId)

        // Odeslání dat do modelu
        model.addAttribute("staff", staff)
        model.addAttribute("salaryResult", salaryResult)

        return "staff_details"  // Šablona pro zobrazení detailů
    }

    // Funkce pro výpočet mzdy
    private fun calculateSalary(staffId: Long): String {
        val query = entityManager.createNativeQuery("SELECT ST69589.calculate_salary(:staffId) FROM DUAL")
        query.setParameter("staffId", staffId)
        return query.singleResult as String
    }
}
