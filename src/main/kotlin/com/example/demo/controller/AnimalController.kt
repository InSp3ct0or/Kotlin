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

    // Просмотр всех животных (для пользователей)
    @GetMapping
    fun listAnimals(model: Model): String {
        val animals = animalRepository.findAll()
        model.addAttribute("animals", animals)
        return "animal_list"
    }

    // Добавление нового животного (для администраторов)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    fun showAddAnimalForm(model: Model): String {
        val enclosures = enclosureRepository.findAll()  // Получаем список всех вольеров
        model.addAttribute("enclosures", enclosures)
        return "animal_form"  // Шаблон для формы
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
            // Проверяем, существует ли вольер
            val enclosure = enclosureRepository.findById(enclosureId).orElseThrow { IllegalArgumentException("Вольер не найден") }

            // Создаем объект Animal
            val animal = Animal(
                name = name,
                species = species,
                birthdate = java.sql.Date.valueOf(birthdate),
                gender = gender,
                enclosure = enclosure
            )

            // Сохраняем животное в базу данных
            animalRepository.save(animal)

            model.addAttribute("message", "Животное добавлено успешно!")
            return "redirect:/animal"
        } catch (e: Exception) {
            // Логируем ошибку
            println("Ошибка при добавлении животного: ${e.message}")
            model.addAttribute("message", "Ошибка: ${e.message}")
            return "animal_form"
        }
    }
}



