package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "FOOD", schema = "ST69589")
data class Food(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOODID")
    val foodId: Long = 0,

    @Column(name = "NAME")
    val name: String = "",

    @Column(name = "QUANTITY")
    val quantity: Int = 0,

    @Column(name = "EXPIRYDATE")
    val expiryDate: java.sql.Date = java.sql.Date.valueOf("2000-01-01"),

    @ManyToOne
    @JoinColumn(name = "VENDOR_VENDORID", referencedColumnName = "VENDORID")
    val vendor: Vendor? = null
) {
    constructor() : this(0, "", 0, java.sql.Date.valueOf("2000-01-01"), null)
}