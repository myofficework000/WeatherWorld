package com.example.weather_world.di

import com.example.weather_world.model.remote.services.ApiServiceWeather
import com.example.weather_world.model.repositories.weather.Constants.BASE_URL
import com.example.weather_world.model.repositories.weather.Repository
import com.example.weather_world.model.repositories.weather.RepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRxJavaAdapterFactory(): CallAdapter.Factory {
        return RxJava3CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideClients(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        callAdapter: CallAdapter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapter)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): ApiServiceWeather {
        return retrofit.create(ApiServiceWeather::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiServiceWeather): Repository {
        return RepositoryImplementation(apiService)
    }
}