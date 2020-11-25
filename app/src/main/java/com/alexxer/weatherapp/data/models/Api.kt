package com.alexxer.weatherapp.data.models

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    //    fun getCurrentWeather(@Path("lat") lat: Double, @Path("lon") lon: Double, @Path("API key") ApiKey:String):Response<Weather>
//@GET("weather?lat={lat}&lon={lon}&appid={API key}&units=metric")
    @GET("weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") unit: String = "metric"
    ): Single<JsonObject>


    @GET("forecast")
    fun getForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") unit: String = "metric"
    ): Single<JsonObject>
}