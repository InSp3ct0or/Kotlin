package com.example.demo.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "EVENT")
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENTID")
    val eventId: Long = 0,

    @Column(name = "NAME")
    val name: String = "",

    @Column(name = "EVENT_DATE")
    val eventDate: Date = Date(),

    @Column(name = "DESCRIPTION")
    val description: String? = null,

    @Column(name = "ATTENDEES")
    val attendees: Int? = null
) {

    constructor() : this(0, "", Date(), null, null)
}