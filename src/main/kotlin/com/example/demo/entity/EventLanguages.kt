package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "EVENT_LANGUAGES")
data class EventLanguages(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LANGUAGE_ID")
    val languageId: Long = 0,

    @ManyToOne
    @JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENTID")
    val event: Event = Event(),

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID", referencedColumnName = "LANGUAGE_ID", insertable = false, updatable = false)
    val language: Language = Language()
) {
    // Secondary constructor with no arguments
    constructor() : this(0, Event(), Language())
}