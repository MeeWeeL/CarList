package com.meeweel.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.meeweel.core.domain.CarBrand
import com.meeweel.core.domain.CarColor

@Entity
data class RoomCarEntity(
    @PrimaryKey(autoGenerate = true)
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