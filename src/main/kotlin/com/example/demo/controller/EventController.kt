package com.example.demo.controller

import com.example.demo.entity.Event
import com.example.demo.entity.EventLanguages
import com.example.demo.entity.Language
import com.example.demo.repository.EventRepository
import com.example.demo.repository.EventLanguagesRepository
import com.example.demo.repository.LanguageRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/event")
class EventController(
    private val eventRepository: EventRepository,
    private val eventLanguagesRepository: EventLanguagesRepository,
    private val languageRepository: LanguageRepository
) {

    // Akce
    @GetMapping
    fun listEvents(model: Model): String {
        val events: List<Event> = eventRepository.findAll()
        model.addAttribute("events", events)
        return "events"
    }

    // Jazyky akce
    @GetMapping("/{eventId}")
    fun getEventLanguages(@PathVariable eventId: Long, model: Model): String {
        val event = eventRepository.findById(eventId).orElseThrow { IllegalArgumentException("Akce nenalezena") }
        val languages = eventLanguagesRepository.findByEvent(event)  // Spojení akce a jazyků
        model.addAttribute("event", event)
        model.addAttribute("languages", languages)
        return "event_languages"
    }

    // Přidání jazyka
    @PostMapping("/{eventId}/add-language")
    fun addLanguageToEvent(
        @PathVariable eventId: Long,  // Získání eventId z URL
        @RequestParam languageId: Long?,  // Získání ID jazyka z parametru
        model: Model
    ): String {
        val event = eventRepository.findById(eventId).orElseThrow { IllegalArgumentException("Akce nenalezena") }
        val language = languageRepository.findById(languageId ?: 1L).orElseThrow { IllegalArgumentException("Jazyk nenalezen") }  // Výchozí jazyk

        val eventLanguage = EventLanguages(event = event, language = language ?: Language()) // Spojení akce a jazyka
        eventLanguagesRepository.save(eventLanguage)
        model.addAttribute("message", "Jazyk úspěšně přidán!")

        return "redirect:/event/$eventId"
    }
}
