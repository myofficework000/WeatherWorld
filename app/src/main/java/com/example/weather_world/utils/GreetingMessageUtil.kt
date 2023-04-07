package com.example.weather_world.utils

import java.util.*

object GreetingMessageUtil {
    fun greetMessage(): String {
        val cal = Calendar.getInstance()
        val timeOfDay = cal.get(Calendar.HOUR_OF_DAY)

        return when(timeOfDay){
            in 0..11 -> "Good Morning"

            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            in 21..23 -> "Good Night"
            else -> "Hi"
        }
    }
}