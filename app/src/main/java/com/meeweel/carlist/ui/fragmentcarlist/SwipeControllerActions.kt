package com.meeweel.carlist.ui.fragmentcarlist

import com.meeweel.carlist.domain.CarModel

interface SwipeControllerActions {

    fun onSwipeLeft(car: CarModel)
}