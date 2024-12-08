package com.example.demo.repository

import com.example.demo.entity.Enclosure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EnclosureRepository : JpaRepository<Enclosure, Long>
