package com.example.demo.controller

import com.example.demo.entity.Enclosure
import com.example.demo.repository.EnclosureRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/enclosure")
class EnclosureController(private val enclosureRepository: EnclosureRepository) {

    @GetMapping
    fun listEnclosures(model: Model): String {
        val enclosures = enclosureRepository.findAll()
        model.addAttribute("enclosures", enclosures)
        return "enclosure_list"
    }

    @GetMapping("/add")
    fun showAddEnclosureForm(model: Model): String {
        model.addAttribute("enclosure", Enclosure())
        return "enclosure_form"
    }

    @PostMapping("/add")
    fun addEnclosure(@ModelAttribute enclosure: Enclosure): String {
        enclosureRepository.save(enclosure)
        return "redirect:/admin/enclosure"
    }

    @GetMapping("/edit/{id}")
    fun showEditEnclosureForm(@PathVariable id: Long, model: Model): String {
        val enclosure = enclosureRepository.findById(id).orElseThrow()
        model.addAttribute("enclosure", enclosure)
        return "enclosure_form"
    }

    @PostMapping("/edit/{id}")
    fun editEnclosure(@PathVariable id: Long, @ModelAttribute enclosure: Enclosure): String {
        enclosureRepository.save(enclosure)
        return "redirect:/admin/enclosure"
    }

    @PostMapping("/delete/{id}")
    fun deleteEnclosure(@PathVariable id: Long): String {
        enclosureRepository.deleteById(id)
        return "redirect:/admin/enclosure"
    }
}