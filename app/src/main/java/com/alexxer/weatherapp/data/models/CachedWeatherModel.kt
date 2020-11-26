package com.alexxer.weatherapp.data.models

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single

class CachedWeatherModel(context: Context) : WeatherModel {

    private val PREFS_NAME = "Weather prefs"

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    private val gson: Gson = Gson()

    override fun getCurrentWeather(lat: Double, lon: Double): Single<Weather> {
        return Single
            .fromCallable {
                val res = sharedPreferences.getString("CURRENT_WEATHER", "")
                return@fromCallable gson.fromJson(res, Weather::class.java)!!
            }
    }

    override fun getForecastWeather(lat: Double, lon: Double): Single<List<Forecast>> {
        return Single
            .fromCallable {
                val res = sharedPreferences.getString("FORECAST_WEATHER", "")
                val listForecastType = object : TypeToken<List<Forecast>>() {}.type
                return@fromCallable gson.fromJson<List<Forecast>>(res, listForecastType)
            }
    }

    fun saveCurrentWeather(weather: Weather) {
        editor.putString("CURRENT_WEATHER", gson.toJson(weather).toString()).commit()
    }

    fun saveForecastWeather(forecasts: List<Forecast>) {
        editor.putString("FORECAST_WEATHER", gson.toJson(forecasts).toString()).commit()
    }

}

