package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long = 0,

    @Column(nullable = false, unique = true)
    val username: String = "",

    @Column(nullable = false)
    val password: String = "",

    @Column(nullable = false)
    val role: String = ""
) {
    // Secondary constructor with no arguments
    constructor() : this(0, "", "", "")
}