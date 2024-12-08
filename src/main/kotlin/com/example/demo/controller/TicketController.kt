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

    // Zobrazení formuláře pro výběr události
    @GetMapping
    fun showTicketForm(model: Model): String {
        val events = eventRepository.findAll()
        model.addAttribute("events", events)
        return "buy_ticket"
    }

    // Formulář pro zadání údajů o návštěvníkovi
    @PostMapping
    fun showVisitorForm(@RequestParam eventId: Long, model: Model): String {
        val event = eventRepository.findById(eventId).orElseThrow { IllegalArgumentException("Událost nenalezena") }
        val languages = languageRepository.findAll()
        model.addAttribute("event", event)
        model.addAttribute("languages", languages)
        return "visitor_form"
    }

    // Zpracování nákupu lístku
    @PostMapping("/process-ticket")
    fun processTicketPurchase(
        @RequestParam eventId: Long,
        @RequestParam languageId: Long,
        @RequestParam age: Int,
        @RequestParam name: String,
        @RequestParam contactInformation: String?,
        model: Model
    ): String {
        // Kontrola věku
        if (age <= 0) {
            model.addAttribute("message", "Věk musí být větší než 0.")
            return "visitor_form"
        }

        // Uložení údajů o návštěvníkovi
        val visitor = Visitor(name = name, age = age, contactInformation = contactInformation)
        val savedVisitor = visitorRepository.save(visitor)

        // Získání události a jazyka
        val event = eventRepository.findById(eventId).orElseThrow { IllegalArgumentException("Událost nenalezena") }
        val language = languageRepository.findById(languageId).orElseThrow { IllegalArgumentException("Jazyk nenalezen") }

        // Cena lístku
        val price = 20.0

        // Vytvoření lístku
        val ticket = Ticket(
            price = price,  // Předpokládaná cena
            visitDate = java.util.Date(),
            visitor = savedVisitor,
            event = event,
            language = language
        )

        ticketRepository.save(ticket)

        // Odeslání dat do modelu
        model.addAttribute("visitor", savedVisitor)
        model.addAttribute("event", event)
        model.addAttribute("language", language)
        model.addAttribute("price", price)  // Cena lístku

        return "ticket_success"  // Šablona pro úspěšný nákup
    }
}
