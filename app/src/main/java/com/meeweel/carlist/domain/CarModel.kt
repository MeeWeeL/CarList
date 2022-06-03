package com.meeweel.carlist.domain

data class CarModel(
    val id: Int,
    val brand: CarBrand,
    val model: String,
    val image: String,
    val isNew: Boolean,
    val mileage: Int,
    val color: CarColor,
    val cost: Int,
)