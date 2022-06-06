package com.meeweel.core.domain

sealed class CarListState {
    data class Success(val carList: List<CarModel>) : CarListState()
    class Error(val error: Throwable) : CarListState()
    object Loading : CarListState()
}