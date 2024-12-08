package com.example.demo.service

import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import jakarta.persistence.Query



@Service
class SalaryService(
    @Autowired val entityManager: EntityManager
) {

    fun calculateSalary(staffId: Long): String {
        val query: Query = entityManager.createNativeQuery(
            "SELECT calculate_salary(:staffId) FROM dual"
        )
        query.setParameter("staffId", staffId)
        val result = query.singleResult
        return result.toString()
    }
}
