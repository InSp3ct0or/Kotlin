package com.example.demo.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "TICKET")
data class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKETID")
    val ticketId: Long = 0,

    @Column(name = "PRICE")
    val price: Double = 0.0,

    @Column(name = "VISIT_DATE")
    val visitDate: Date = Date(),

    @ManyToOne
    @JoinColumn(name = "VISITOR_ID", referencedColumnName = "VISITORID")
    val visitor: Visitor = Visitor(),

    @ManyToOne
    @JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENTID")
    val event: Event = Event(),

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID", referencedColumnName = "LANGUAGE_ID")
    val language: Language = Language()
) {
    // No-arg constructor
    constructor() : this(0, 0.0, Date(), Visitor(), Event(), Language())
}
