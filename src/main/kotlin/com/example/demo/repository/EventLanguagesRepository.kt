package com.example.demo.repository

import com.example.demo.entity.Event
import com.example.demo.entity.EventLanguages
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventLanguagesRepository : JpaRepository<EventLanguages, Long> {

    fun findByEvent(event: Event): List<EventLanguages>


}
