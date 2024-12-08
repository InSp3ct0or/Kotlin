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

    // Просмотр всех мероприятий
    @GetMapping
    fun listEvents(model: Model): String {
        val events: List<Event> = eventRepository.findAll()
        model.addAttribute("events", events)
        return "events"
    }

    // Просмотр языков для конкретного мероприятия
    @GetMapping("/{eventId}")
    fun getEventLanguages(@PathVariable eventId: Long, model: Model): String {
        val event = eventRepository.findById(eventId).orElseThrow { IllegalArgumentException("Событие не найдено") }
        val languages = eventLanguagesRepository.findByEvent(event)  // Получаем связи событий с языками
        model.addAttribute("event", event)
        model.addAttribute("languages", languages)
        return "event_languages"
    }


    // Добавление нового языка для события
    @PostMapping("/{eventId}/add-language")
    fun addLanguageToEvent(
        @PathVariable eventId: Long,  // Получаем eventId из URL
        @RequestParam languageId: Long?,  // Получаем ID языка из параметров запроса
        model: Model
    ): String {
        val event = eventRepository.findById(eventId).orElseThrow { IllegalArgumentException("Событие не найдено") }
        val language = languageRepository.findById(languageId ?: 1L).orElseThrow { IllegalArgumentException("Язык не найден") }  // Если язык не выбран, используем язык по умолчанию

        val eventLanguage = EventLanguages(event = event, language = language ?: Language()) // Создаем связь между событием и языком
        eventLanguagesRepository.save(eventLanguage)
        model.addAttribute("message", "Language added successfully!")

        return "redirect:/event/$eventId"
    }
}
