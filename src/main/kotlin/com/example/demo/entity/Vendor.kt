package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "VENDOR", schema = "ST69589")
data class Vendor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VENDORID")
    val vendorId: Long = 0,

    @Column(name = "NAME")
    val name: String = "",

    @Column(name = "CONTACTINFORMATION")
    val contactInformation: String = ""
)
