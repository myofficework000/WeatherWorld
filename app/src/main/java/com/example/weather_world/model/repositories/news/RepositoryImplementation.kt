package com.example.weather_world.model.repositories.news

import com.example.weather_world.model.remote.data.news.NewsResponse
import com.example.weather_world.model.remote.services.ApiServiceNews
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(private val apiService: ApiServiceNews) :
    Repository {

    override fun fetchNewsAPI(country: String): Single<NewsResponse> {
        return apiService.getNewsByRegion(country)
    }
}