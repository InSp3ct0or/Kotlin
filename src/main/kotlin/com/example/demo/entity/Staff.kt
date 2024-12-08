package com.example.demo.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Column

@Entity
@Table(name = "STAFF", schema = "ST69589")
data class Staff(
    @Id
    @Column(name = "STAFFID")
    val staffId: Long = 0L,

    @Column(name = "NAME")
    val name: String = "",

    @Column(name = "POSITION")
    val position: String = "",

    @Column(name = "CONTACTINFORMATION")
    val contactInformation: String = ""
) {

    constructor() : this(0L, "", "", "")
}