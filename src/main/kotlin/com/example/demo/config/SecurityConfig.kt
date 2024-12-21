package com.example.demo.config

import com.example.demo.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(
    private val customUserDetailsService: CustomUserDetailsService
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance() //no hash pass
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/admin/**").hasRole("ADMIN") // Добавляем доступ для ADMIN к эндпоинтам admin/*
                    .requestMatchers("/animal/**").hasAnyRole("WORKER", "ADMIN") // WORKER и ADMIN
                    .requestMatchers("/ticket/**").hasAnyRole("USER", "ADMIN") // USER и ADMIN
                    .anyRequest().permitAll() // Все остальные запросы доступны без авторизации
            }
            .formLogin { formLogin -> formLogin.defaultSuccessUrl("/", true) }
            .logout { logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/") }
        return http.build()
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }
}
