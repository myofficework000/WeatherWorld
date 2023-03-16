package com.example.weatherappall.model.remote.data.forecast

data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherDetail>,
    val message: Int
)