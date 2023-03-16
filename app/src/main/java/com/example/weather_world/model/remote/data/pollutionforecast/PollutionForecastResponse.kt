package com.example.weatherappall.model.remote.data.pollutionforecast

data class PollutionForecastResponse(
    val coord: Coord,
    val list: List<Pollution>
)