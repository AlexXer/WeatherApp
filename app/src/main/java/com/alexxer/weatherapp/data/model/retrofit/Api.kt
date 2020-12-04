package com.alexxer.weatherapp.data.model.retrofit

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") unit: String = "metric"
    ): Single<JsonObject>

    @GET("forecast")
    fun getForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") unit: String = "metric"
    ): Single<JsonObject>

    companion object {
        private const val API_KEY = "89397e33eaecc4a607d8e7436c770ba8"
    }
}