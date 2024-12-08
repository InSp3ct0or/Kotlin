package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "VISITOR")
data class Visitor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VISITORID")
    val visitorId: Long = 0,

    @Column(name = "NAME")
    val name: String = "",

    @Column(name = "AGE")
    val age: Int = 0,

    @Column(name = "CONTACTINFORMATION")
    val contactInformation: String? = null
) {

    constructor() : this(0, "", 0, null)
}