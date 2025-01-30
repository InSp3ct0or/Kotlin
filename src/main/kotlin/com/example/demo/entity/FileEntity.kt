package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "FILES")
data class FileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "NAME")
    val name: String,

    @Lob
    @Column(name = "DATA", columnDefinition = "BLOB")
    val data: ByteArray
) {
    // Публичный конструктор без аргументов
    constructor() : this(0, "", ByteArray(0))
}