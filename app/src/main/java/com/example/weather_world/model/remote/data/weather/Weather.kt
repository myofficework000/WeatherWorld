package com.example.weatherappall.model.remote.data.weather

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)