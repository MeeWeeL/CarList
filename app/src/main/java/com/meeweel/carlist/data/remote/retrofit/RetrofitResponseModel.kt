package com.meeweel.carlist.data.remote.retrofit

import com.meeweel.carlist.domain.CarBrand
import com.meeweel.carlist.domain.CarColor
import com.meeweel.carlist.domain.CarModel

data class RetrofitResponseModel(
    val id: Int,
    val brand: String,
    val model: String,
    val year: Int,
    val image: String,
    val isNew: Boolean,
    val mileage: Int,
    val color: String,
    val cost: Int
    )

fun RetrofitResponseModel.toCarModel() : CarModel {
    val color: CarColor = when (this.color) {
        CarColor.RED.color -> CarColor.RED
        CarColor.WHITE.color -> CarColor.WHITE
        CarColor.BLACK.color -> CarColor.BLACK
        CarColor.BLUE.color -> CarColor.BLUE
        CarColor.GREEN.color -> CarColor.GREEN
        else -> CarColor.UNKNOWN
    }
    val brand: CarBrand = when (this.brand) {
        CarBrand.BMW.brand -> CarBrand.BMW
        CarBrand.AUDI.brand -> CarBrand.AUDI
        CarBrand.MERCEDES.brand -> CarBrand.MERCEDES
        CarBrand.KIA.brand -> CarBrand.KIA
        else -> CarBrand.UNKNOWN
    }
    return CarModel(
        id = this.id,
        brand = brand,
        model = this.model,
        year = this.year,
        image = this.image,
        isNew = this.isNew,
        mileage = this.mileage,
        color = color,
        cost = this.cost
    )
}

fun List<RetrofitResponseModel>.toCarModelList() : List<CarModel> = this.map { it.toCarModel() }