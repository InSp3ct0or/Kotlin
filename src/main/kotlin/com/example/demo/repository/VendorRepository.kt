package com.example.demo.repository

import com.example.demo.entity.Vendor
import org.springframework.data.jpa.repository.JpaRepository

interface VendorRepository : JpaRepository<Vendor, Long>
