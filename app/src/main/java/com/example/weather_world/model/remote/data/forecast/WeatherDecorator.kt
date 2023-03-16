package com.example.weatherappall.model.remote.data.forecast

data class WeatherDecorator(
    val tempText:String,
    val speedText:String,
    val icon:String
){
    companion object{
        fun decorate(weatherDetail: WeatherDetail): WeatherDecorator {
            return WeatherDecorator(
                tempText= weatherDetail.main.temp.toString()+ '℃'.toString(),
                speedText = weatherDetail.wind.speed.toString() + " km/h",
                icon = weatherDetail.weather[0].icon
            )
        }
    }
}
