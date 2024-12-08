package com.example.demo.entity

import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "ANIMAL")
data class Animal(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANIMALID")
    val animalId: Long = 0,

    @Column(name = "NAME")
    val name: String = "",

    @Column(name = "SPECIES")
    val species: String = "",

    @Column(name = "BIRTHDATE")
    val birthdate: Date = Date(0),

    @Column(name = "GENDER")
    val gender: String = "",

    @ManyToOne
    @JoinColumn(name = "ENCLOSURE_ENCLOSUREID")
    val enclosure: Enclosure = Enclosure()
)