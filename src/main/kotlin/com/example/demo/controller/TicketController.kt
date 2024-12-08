package com.example.demo.controller

import com.example.demo.entity.Event
import com.example.demo.entity.Language
import com.example.demo.entity.Ticket
import com.example.demo.entity.Visitor
import com.example.demo.repository.EventRepository
import com.example.demo.repository.LanguageRepository
import com.example.demo.repository.TicketRepository
import com.example.demo.repository.VisitorRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/buy-ticket")
class TicketController(
    private val ticketRepository: TicketRepository,
    private val eventRepository: EventRepository,
    private val languageRepository: LanguageRepository,
    private val visitorRepository: VisitorRepository
) {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    // Шаблон для отображения событий и выбора
    @GetMapping
    fun showTicketForm(model: Model): String {
        val events = eventRepository.findAll()
        model.addAttribute("events", events)
        return "buy_ticket"
    }

    // После выбора события и языка, отображаем форму для ввода данных посетителя
    @PostMapping
    fun showVisitorForm(@RequestParam eventId: Long, model: Model): String {
        val event = eventRepository.findById(eventId).orElseThrow { IllegalArgumentException("Событие не найдено") }
        val languages = languageRepository.findAll()
        model.addAttribute("event", event)
        model.addAttribute("languages", languages)
        return "visitor_form"
    }

    @PostMapping("/process-ticket")
    fun processTicketPurchase(
        @RequestParam eventId: Long,
        @RequestParam languageId: Long,
        @RequestParam age: Int,
        @RequestParam name: String,
        @RequestParam contactInformation: String?,
        model: Model
    ): String {
        // Проверка возраста
        if (age <= 0) {
            model.addAttribute("message", "Возраст должен быть больше 0.")
            return "visitor_form"
        }

        // Сохранение данных посетителя
        val visitor = Visitor(name = name, age = age, contactInformation = contactInformation)
        val savedVisitor = visitorRepository.save(visitor)

        // Получаем событие и язык
        val event = eventRepository.findById(eventId).orElseThrow { IllegalArgumentException("Событие не найдено") }
        val language = languageRepository.findById(languageId).orElseThrow { IllegalArgumentException("Язык не найден") }

        // Цена билета
        val price = 20.0

        // Создание билета
        val ticket = Ticket(
            price = price,  // Примерная цена
            visitDate = java.util.Date(),
            visitor = savedVisitor,
            event = event,
            language = language
        )

        ticketRepository.save(ticket)

        // Передаем в модель данные для отображения
        model.addAttribute("visitor", savedVisitor)
        model.addAttribute("event", event)
        model.addAttribute("language", language)
        model.addAttribute("price", price)  // Передаем цену билета

        return "ticket_success"  // Шаблон успешной покупки
    }


}
