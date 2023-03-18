package com.example.weather_world.model.remote.services

import com.example.weather_world.model.remote.data.city.CityResponse
import com.example.weather_world.model.repositories.weather.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGeo {
    @GET("direct")
    fun getCoordByCity(
        @Query("q") q: String,
        @Query("appid") appid: String = Constants.API_KEY
    ): Single<List<CityResponse>>
}