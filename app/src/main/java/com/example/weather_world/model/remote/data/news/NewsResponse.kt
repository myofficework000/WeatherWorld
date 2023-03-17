package com.example.weather_world.model.remote.data.news

data class NewsResponse(
    val news: List<News>,
    val status: String
)