package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class GameController {

    @GetMapping("/game")
    fun gamePage(): String {
        return "game" // Этот файл будет лежать в папке src/main/resources/templates
    }
}