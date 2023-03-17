package com.example.weather_world.model.remote.services

import com.example.weather_world.model.remote.data.news.NewsResponse
import com.example.weather_world.model.repositories.news.Constant.END_POINT_SEARCH
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceNews {
    @GET(END_POINT_SEARCH)
    fun getNewsByRegion(
        @Query("country") country: String,
    ): Single<NewsResponse>
}