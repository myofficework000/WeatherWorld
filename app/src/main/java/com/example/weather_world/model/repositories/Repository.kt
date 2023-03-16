package com.example.mvvmcompose.model.repositories

import com.example.weather_world.model.remote.dto.ApiResponse
import io.reactivex.rxjava3.core.Observable

interface Repository {
    fun makeAPICall(): Observable<ApiResponse>
}