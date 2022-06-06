package com.meeweel.data.remote.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface CarsApi {

    @GET("/api.php?q=get_car_list")
    @Headers("Content-type: application/json")
    fun getCarList(): Single<List<RetrofitResponseModel>>
}