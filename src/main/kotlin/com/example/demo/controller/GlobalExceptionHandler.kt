package com.example.demo.controller

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.ui.Model

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception, model: Model): String {
        model.addAttribute("errorMessage", "Došlo k chybě: " + e.message)
        return "global_error"
    }
}
