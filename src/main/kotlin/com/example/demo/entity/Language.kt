package com.example.demo.entity

import jakarta.persistence.*


@Entity
@Table(name = "LANGUAGES")
data class Language(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LANGUAGE_ID")
    val languageId: Long = 0,

    @Column(name = "LANGUAGE_NAME")
    val languageName: String
)
 {

    constructor() : this(0, "")
}