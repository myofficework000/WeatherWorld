package com.example.weather_world.model.repositories.weather

import android.location.Location
import com.example.weather_world.model.remote.data.city.CityResponse
import com.example.weather_world.model.remote.services.ApiGeo
import com.example.weather_world.model.remote.services.ApiServiceWeather
import com.example.weatherappall.model.remote.data.forecast.ForecastResponse
import com.example.weatherappall.model.remote.data.pollutionforecast.PollutionForecastResponse
import com.example.weatherappall.model.remote.data.weather.WeatherResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val apiService: ApiServiceWeather,
    private val apiGeo: ApiGeo) : Repository {

    override fun fetchWeatherAPI(city: String): Single<WeatherResponse> {
        return apiService.getWeather(city = city)
    }

    override fun fetchForecastAPI(city: String): Single<ForecastResponse> {
        return apiService.getForecast(city = city)
    }

    override fun fetchAirPollutionAPI(location: Location): Single<PollutionForecastResponse> {
        return apiService.getPollutionForecast(location.latitude, location.longitude)
    }

    override fun fetchCoordByCity(city: String): Single<List<CityResponse>> =
        apiGeo.getCoordByCity(city)

}