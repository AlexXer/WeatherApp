package com.alexxer.weatherapp.data.model

data class Weather(
    val country: String?,
    val town: String?,
    val description:String?,
    val temperature: Double?,
    val humidity: Double?,
    val pressure: Double?,
    val windSpeed: Double?,
    val windDirection: Double?,
    val precipitation: Double?,
    val iconId: String?
)
