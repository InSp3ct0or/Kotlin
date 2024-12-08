package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ModelAttribute

@Controller
class RegistrationController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(@ModelAttribute user: User, model: Model): String {
        // Проверка на уникальность логина
        val existingUser = userRepository.findByUsername(user.username)
        if (existingUser != null) {
            model.addAttribute("error", "Пользователь с таким логином уже существует.")
            return "register"  // Возвращаем на форму регистрации с ошибкой
        }

        // Хешируем пароль и сохраняем пользователя
        val newUser = User(
            username = user.username,
            password = passwordEncoder.encode(user.password),  // Хешируем пароль
            role = "ROLE_USER" // Устанавливаем роль по умолчанию
        )
        userRepository.save(newUser)

        // Сообщение об успешной регистрации
        model.addAttribute("message", "Регистрация успешна! Теперь вы можете войти в систему.")
        return "registration_success"  // Перенаправление на страницу успешной регистрации
    }
}
