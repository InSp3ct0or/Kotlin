package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "ENCLOSURE")
data class Enclosure(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENCLOSUREID")  // Название столбца в базе данных
    val enclosureId: Long = 0,

    @Column(name = "TYPE")  // Тип вольера
    val type: String,

    @Column(name = "LOCATION")  // Местоположение вольера
    val location: String,

    @Column(name = "CAPACITY")  // Вместимость вольера
    val capacity: Int
)
{
    // Конструктор без параметров для JPA
    constructor() : this(0, "", "", 0)
}
