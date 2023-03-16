package com.example.weather_world.model.remote.data.weather

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)