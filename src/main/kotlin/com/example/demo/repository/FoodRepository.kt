package com.example.demo.repository

import com.example.demo.entity.Food
import org.springframework.data.jpa.repository.JpaRepository

interface FoodRepository : JpaRepository<Food, Long>
