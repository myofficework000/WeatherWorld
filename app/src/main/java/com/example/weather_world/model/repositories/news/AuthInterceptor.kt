package com.example.weather_world.model.repositories.news

import com.example.weather_world.model.repositories.news.Constant.AUTHORIZATION
import com.example.weather_world.model.repositories.news.Constant.TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request().newBuilder()
        currentRequest.addHeader(AUTHORIZATION, TOKEN)

        val newRequest = currentRequest.build()
        return chain.proceed(newRequest)
    }
}