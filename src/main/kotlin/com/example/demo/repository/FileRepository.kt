package com.example.demo.repository

import com.example.demo.entity.BinaryContent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<BinaryContent, Long>
