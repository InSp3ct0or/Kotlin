package com.example.demo.controller

import com.example.demo.entity.Food
import com.example.demo.repository.FoodRepository
import com.example.demo.repository.VendorRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/food")
class FoodController(
    private val foodRepository: FoodRepository,
    private val vendorRepository: VendorRepository
) {

    @GetMapping
    fun listFood(model: Model): String {
        val foodList = foodRepository.findAll()
        model.addAttribute("foodList", foodList)
        return "food_list"
    }

    @GetMapping("/add")
    fun showAddFoodForm(model: Model): String {
        model.addAttribute("food", Food())
        model.addAttribute("vendors", vendorRepository.findAll())
        return "food_form"
    }

    @PostMapping("/add")
    fun addFood(@ModelAttribute food: Food): String {
        foodRepository.save(food)
        return "redirect:/admin/food"
    }

    @GetMapping("/edit/{id}")
    fun showEditFoodForm(@PathVariable id: Long, model: Model): String {
        val food = foodRepository.findById(id).orElseThrow()
        model.addAttribute("food", food)
        model.addAttribute("vendors", vendorRepository.findAll())
        return "food_form"
    }

    @PostMapping("/edit/{id}")
    fun editFood(@PathVariable id: Long, @ModelAttribute food: Food): String {
        foodRepository.save(food)
        return "redirect:/admin/food"
    }

    @PostMapping("/delete/{id}")
    fun deleteFood(@PathVariable id: Long, model: Model): String {
        return try {
            foodRepository.deleteById(id)
            "redirect:/admin/food"
        } catch (e: DataIntegrityViolationException) {
            model.addAttribute("errorMessage", "Nelze odstranit jídlo, protože je již používáno v ohradě se zvířetem.")
            "food_error"
        }
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolation(e: DataIntegrityViolationException, model: Model): String {
        model.addAttribute("errorMessage", "Nelze odstranit jídlo, protože je již používáno v ohradě se zvířetem.")
        return "food_error"
    }
}
