package com.example.demo.controller

import com.example.demo.entity.Ticket
import com.example.demo.repository.TicketRepository
import com.example.demo.repository.VisitorRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/my-tickets")
class MyTicketsController(
    private val ticketRepository: TicketRepository,
    private val visitorRepository: VisitorRepository
) {

    @GetMapping
    fun viewTickets(model: Model): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val visitorId = authentication.name.toLongOrNull()

        if (visitorId == null) {
            model.addAttribute("message", "Uživatelský identifikátor nebyl nalezen.")
            model.addAttribute("tickets", emptyList<Ticket>())
            return "my_tickets"
        }

        val visitor = visitorRepository.findById(visitorId).orElse(null)

        if (visitor == null) {
            model.addAttribute("message", "Návštěvník s ID $visitorId nebyl nalezen.")
            model.addAttribute("tickets", emptyList<Ticket>())
            return "my_tickets"
        }

        val tickets = ticketRepository.findByVisitor(visitor)
        model.addAttribute("tickets", tickets)
        return "my_tickets"
    }
}
