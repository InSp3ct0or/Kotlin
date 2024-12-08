package com.example.demo.repository

import com.example.demo.entity.Visitor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository



@Repository
interface VisitorRepository : JpaRepository<Visitor, Long>

