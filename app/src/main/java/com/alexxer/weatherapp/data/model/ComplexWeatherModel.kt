package com.alexxer.weatherapp.data.model

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class ComplexWeatherModel(
    private val cachedWeatherModel: CachedWeatherModel,
    private val remoteWeatherModel: RemoteWeatherModel
) : WeatherModel {
    override fun getCurrentWeather(lat: Double, lon: Double): Single<Weather> {
        return remoteWeatherModel
            .getCurrentWeather(lat, lon)
            .onErrorResumeNext { cachedWeatherModel.getCurrentWeather(lat, lon) }
            .doOnSuccess { weather -> cachedWeatherModel.saveCurrentWeather(weather) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getForecastWeather(lat: Double, lon: Double): Single<List<Forecast>> {
        return remoteWeatherModel
            .getForecastWeather(lat, lon)
            .doOnSuccess { forecasts -> cachedWeatherModel.saveForecastWeather(forecasts) }
            .onErrorResumeNext { cachedWeatherModel.getForecastWeather(lat, lon) }
            .observeOn(AndroidSchedulers.mainThread())
    }
}