package com.example.weather_world.model.remote.services

import com.example.weather_world.model.repositories.weather.Constants.API_KEY
import com.example.weather_world.model.repositories.weather.Constants.UNITS
import com.example.weatherappall.model.remote.data.forecast.ForecastResponse
import com.example.weatherappall.model.remote.data.pollutionforecast.PollutionForecastResponse
import com.example.weatherappall.model.remote.data.weather.WeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceWeather {

    // Alex
    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS
    ): Single<WeatherResponse>

    //Luan also Thomas = same work on diffrent branch
    @GET("forecast")
    fun getForecast(
        @Query("q") city: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS
    ): Single<ForecastResponse>

    //Josh -> add fragments for all 3
    @GET("air_pollution/forecast")
    fun getPollutionForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY,
    ): Single<PollutionForecastResponse>

    //
    @GET("weather")
    fun getCurrentLocationWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS
    ): Single<WeatherResponse>
}