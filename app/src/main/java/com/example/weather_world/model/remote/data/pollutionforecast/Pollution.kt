package com.example.weatherappall.model.remote.data.pollutionforecast

data class Pollution(
    val components: Components,
    val dt: Int,
    val main: Main
)