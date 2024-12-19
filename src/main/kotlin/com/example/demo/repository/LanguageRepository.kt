package com.example.demo.repository

import com.example.demo.entity.Language
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface LanguageRepository : JpaRepository<Language, Long> {
    override fun findById(id: Long): Optional<Language>
}