package com.meeweel.core.domain

data class CarModel(
    val id: Int,
    val brand: CarBrand,
    val model: String,
    val year: Int,
    val image: String,
    val isNew: Boolean,
    val mileage: Int,
    val color: CarColor,
    val cost: Int
)