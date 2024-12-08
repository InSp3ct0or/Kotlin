package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "BINARY_CONTENT", schema = "ST69589")
data class BinaryContent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENT_ID")
    val contentId: Long = 0L,

    @Column(name = "FILE_NAME")
    val fileName: String = "",

    @Column(name = "FILE_TYPE")
    val fileType: String = "",

    @Column(name = "FILE_EXTENSION")
    val fileExtension: String = "",

    @Lob
    @Column(name = "FILE_DATA")
    val fileData: ByteArray = ByteArray(0),

    @Column(name = "UPLOAD_DATE")
    val uploadDate: java.util.Date? = null,

    @Column(name = "MODIFY_DATE")
    val modifyDate: java.util.Date? = null,

    @Column(name = "UPLOADED_BY")
    val uploadedBy: String = ""
)
