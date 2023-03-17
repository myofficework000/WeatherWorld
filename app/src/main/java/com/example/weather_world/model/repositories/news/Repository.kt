package com.example.weather_world.model.repositories.news

import com.example.weather_world.model.remote.data.news.NewsResponse
import io.reactivex.rxjava3.core.Single

interface Repository {

    fun fetchNewsAPI(country: String): Single<NewsResponse>
}