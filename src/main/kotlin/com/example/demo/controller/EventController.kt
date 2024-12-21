package com.example.demo.controller

import com.example.demo.entity.Event
import com.example.demo.repository.EventRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/events")
class EventController(private val eventRepository: EventRepository) {

    @GetMapping
    fun listEvents(model: Model): String {
        model.addAttribute("events", eventRepository.findAll())
        return "events_list"
    }

    @GetMapping("/add")
    fun showAddForm(model: Model): String {
        model.addAttribute("event", Event())
        return "event_form"
    }

    @PostMapping("/save")
    fun saveEvent(@ModelAttribute event: Event): String {
        eventRepository.save(event)
        return "redirect:/admin/events"
    }

    @GetMapping("/edit/{id}")
    fun showEditForm(@PathVariable id: Long, model: Model): String {
        val event = eventRepository.findById(id).orElseThrow { IllegalArgumentException("Událost nenalezena!") }
        model.addAttribute("event", event)
        return "event_form"
    }

    @GetMapping("/delete/{id}")
    fun deleteEvent(@PathVariable id: Long): String {
        eventRepository.deleteById(id)
        return "redirect:/admin/events"
    }


}
