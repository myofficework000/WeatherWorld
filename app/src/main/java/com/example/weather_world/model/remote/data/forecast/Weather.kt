package com.example.weatherappall.model.remote.data.forecast

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)