package com.example.demo.repository

import com.example.demo.entity.Animal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimalRepository : JpaRepository<Animal, Long> {
    fun findByName(name: String): List<Animal>
}
