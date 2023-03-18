package com.example.weather_world.model.remote.data.city


import android.location.Location
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("local_names")
    val localNames: LocalNames,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("state")
    val state: String?
) {
    fun getLocation() = Location("").apply {
        latitude = this@CityResponse.lat
        longitude = this@CityResponse.lon
    }
}