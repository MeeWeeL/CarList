package com.meeweel.data.local.room

import com.meeweel.core.domain.CarModel

fun RoomCarEntity.toCarModel() : CarModel = CarModel(
        id = this.id,
        brand = this.brand,
        model = this.model,
        year = this.year,
        image = this.image,
        isNew = this.isNew,
        mileage = this.mileage,
        color = this.color,
        cost = this.cost
    )

fun List<RoomCarEntity>.toCarModelList() : List<CarModel> =
    this.map { it.toCarModel() }

fun CarModel.toRoomCarEntity() : RoomCarEntity = RoomCarEntity(
    id = this.id,
    brand = this.brand,
    model = this.model,
    year = this.year,
    image = this.image,
    isNew = this.isNew,
    mileage = this.mileage,
    color = this.color,
    cost = this.cost
)

fun List<CarModel>.toRoomCarEntityList() : List<RoomCarEntity> =
    this.map { it.toRoomCarEntity() }