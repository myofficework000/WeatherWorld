package com.example.weather_world.di

import com.example.weather_world.model.remote.services.ApiServiceNews
import com.example.weather_world.model.remote.services.ApiServiceWeather
import com.example.weather_world.model.repositories.news.AuthInterceptor
import com.example.weather_world.model.repositories.news.Constant
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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("WeatherBaseUrl")
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
    @Named("WeatherClients")
    fun provideClients(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    @Named("Weather")
    fun provideRetrofit(
        @Named("WeatherBaseUrl") baseUrl: String,
        converterFactory: Converter.Factory,
        callAdapter: CallAdapter.Factory,
        @Named("WeatherClients") okHttpClient: OkHttpClient
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
    fun provideAPIService(@Named("Weather") retrofit: Retrofit): ApiServiceWeather {
        return retrofit.create(ApiServiceWeather::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiServiceWeather): Repository {
        return RepositoryImplementation(apiService)
    }

    @Singleton
    @Provides
    @Named("NewsBaseUrl")
    fun provideNewsBaseUrl(): String {
        return Constant.BASE_URL
    }

    @Singleton
    @Provides
    @Named("NewsClients")
    fun provideNewsClients(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthInterceptor())
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    @Named("News")
    fun provideNewsRetrofit(
        @Named("NewsBaseUrl") baseUrl: String,
        converterFactory: Converter.Factory,
        callAdapter: CallAdapter.Factory,
        @Named("NewsClients") okHttpClient: OkHttpClient
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
    fun provideNewsAPIService(@Named("News") retrofit: Retrofit): ApiServiceNews {
        return retrofit.create(ApiServiceNews::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(apiService: ApiServiceNews): com.example.weather_world.model.repositories.news.Repository {
        return com.example.weather_world.model.repositories.news.RepositoryImplementation(apiService)
    }
}