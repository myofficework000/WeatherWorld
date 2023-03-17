package com.example.weather_world.model.repositories.weather

import android.location.Location
import com.example.weatherappall.model.remote.data.forecast.ForecastResponse
import com.example.weatherappall.model.remote.data.pollutionforecast.PollutionForecastResponse
import com.example.weatherappall.model.remote.data.weather.WeatherResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface Repository {

    fun fetchForecastAPI(city: String): Single<ForecastResponse>
    fun fetchWeatherAPI(city: String): Single<WeatherResponse>
    fun fetchAirPollutionAPI(location: Location): Single<PollutionForecastResponse>
}