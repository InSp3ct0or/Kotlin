package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "ENCLOSURE", schema = "ST69589")
data class Enclosure(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENCLOSUREID")
    val enclosureId: Long = 0,

    @Column(name = "TYPE")
    val type: String = "",

    @Column(name = "LOCATION")
    val location: String = "",

    @Column(name = "CAPACITY")
    val capacity: Int = 0,

    @ManyToOne
    @JoinColumn(name = "PARENT_ENCLOSURE_ID", referencedColumnName = "ENCLOSUREID")
    val parentEnclosure: Enclosure? = null,

    @Transient
    var treeLevel: Int = 0,

    @Transient
    var subEnclosures: List<Enclosure> = emptyList()
)