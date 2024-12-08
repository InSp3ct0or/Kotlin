package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "ENCLOSURE")
data class Enclosure(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENCLOSUREID")
    val enclosureId: Long = 0,

    @Column(name = "TYPE")
    val type: String,

    @Column(name = "LOCATION")
    val location: String,

    @Column(name = "CAPACITY")
    val capacity: Int
)
{

    constructor() : this(0, "", "", 0)
}
