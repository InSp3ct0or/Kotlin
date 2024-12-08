package com.example.demo.repository

import com.example.demo.entity.Staff
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StaffRepository : JpaRepository<Staff, Long>
