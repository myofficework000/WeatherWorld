package com.example.weather_world.model.repositories.weather

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val GEO_BASE_URL = "https://api.openweathermap.org/geo/1.0/"
    const val API_KEY = "3f9bb0b98f7d19d210e8fb480fc1a8a2"
    const val UNITS = "metric"
    const val IMG_URL = "https://openweathermap.org/img/wn"
    const val TEMP_CONVERTER1 = 1.8
    const val TEMP_CONVERT2 = 32

    val airQualityBarGradient = Brush.horizontalGradient(
        listOf(
            Color(0xff60fe00),
            Color(0xffb9ff00),
            Color(0xfffeff80),
            Color(0xfffca754),
            Color(0xfffe0707)
        )
    )
}