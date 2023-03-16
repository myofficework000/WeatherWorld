package com.example.mvvmcompose.model.repositories

import com.example.weather_world.model.remote.ApiService
import com.example.weather_world.model.remote.dto.ApiResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(private val apiService: ApiService) :
    Repository {

    override fun makeAPICall(): Observable<ApiResponse> {
        return apiService.getResultFromApi()
    }
}