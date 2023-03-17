package com.example.weather_world.viewModel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_world.model.repositories.weather.Repository
import com.example.weatherappall.model.remote.data.forecast.ForecastResponse
import com.example.weatherappall.model.remote.data.forecast.Weather
import com.example.weatherappall.model.remote.data.weather.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private lateinit var compositeDisposable: CompositeDisposable

    private val _forecastResponse = MutableLiveData<ForecastResponse>()
    val forecastResponse: LiveData<ForecastResponse> = _forecastResponse

    private val _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse> = _weatherResponse


    fun getWeatherInfo(city: String) {
        compositeDisposable.add(
            repository.fetchWeatherAPI(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _weatherResponse.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getForeCastInfo(city: String) {
        compositeDisposable.add(
            repository.fetchForecastAPI(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _forecastResponse.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getAirPollutionInfo(location: Location) {
        compositeDisposable.add(
            repository.fetchAirPollutionAPI(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    it.printStackTrace()
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}