package com.example.demo


import java.time.LocalDateTime

data class AnimalDto(
    val name: String,
    val species: String,
    val birthdate: LocalDateTime,
    val gender: String,
    val enclosure: EnclosureDto
)
