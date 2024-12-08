package com.example.demo.controller

import com.example.demo.entity.Animal
import com.example.demo.entity.Enclosure
import com.example.demo.repository.AnimalRepository
import com.example.demo.repository.EnclosureRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/animal")
class AnimalController(
    private val animalRepository: AnimalRepository,
    private val enclosureRepository: EnclosureRepository
) {

    // Zvířata
    @GetMapping
    fun listAnimals(model: Model): String {
        val animals = animalRepository.findAll()
        model.addAttribute("animals", animals)
        return "animal_list"
    }

    // Formulář
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    fun showAddAnimalForm(model: Model): String {
        val enclosures = enclosureRepository.findAll()  // Výběhy
        model.addAttribute("enclosures", enclosures)
        return "animal_form"  // Šablona
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    fun addAnimal(
        @RequestParam name: String,
        @RequestParam species: String,
        @RequestParam birthdate: String,
        @RequestParam gender: String,
        @RequestParam enclosureId: Long,
        model: Model
    ): String {
        try {
            // Ověření
            val enclosure = enclosureRepository.findById(enclosureId).orElseThrow { IllegalArgumentException("Výběh nenalezen") }

            // Vytvoření
            val animal = Animal(
                name = name,
                species = species,
                birthdate = java.sql.Date.valueOf(birthdate),
                gender = gender,
                enclosure = enclosure
            )

            // Uložení
            animalRepository.save(animal)

            model.addAttribute("message", "Zvíře bylo úspěšně přidáno!")
            return "redirect:/animal"
        } catch (e: Exception) {
            // Chyba
            println("Chyba při přidávání zvířete: ${e.message}")
            model.addAttribute("message", "Chyba: ${e.message}")
            return "animal_form"
        }
    }
}
