package com.alexxer.weatherapp.data.model

import io.reactivex.Single

interface WeatherModel {
    fun getCurrentWeather(lat:Double, lon:Double):Single<Weather>
    fun getForecastWeather(lat:Double, lon:Double):Single<List<Forecast>>
}