package com.alexxer.weatherapp.data.model

import com.alexxer.weatherapp.data.model.retrofit.RetrofitInstance
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class RemoteWeatherModel : WeatherModel {
    override fun getCurrentWeather(lat: Double, lon: Double): Single<Weather> {
        return RetrofitInstance
            .api
            .getCurrentWeather(lat, lon)
            .subscribeOn(Schedulers.io())
            .map { json -> extractWeatherFromJson(json) }
    }

    override fun getForecastWeather(lat: Double, lon: Double): Single<List<Forecast>> {
        return RetrofitInstance
            .api
            .getForecastWeather(lat, lon)
            .subscribeOn(Schedulers.io())
            .map { json -> extractForecastFromJson(json) }
    }

    private fun extractForecastFromJson(json: JsonObject): ArrayList<Forecast> {
        val forecasts = arrayListOf<Forecast>()
        val array = json.get("list")?.asJsonArray
        array?.forEach {
            val root = it.asJsonObject
            val temperature = root.get("main").asJsonObject.get("temp").asDouble
            val dateTime = root.get("dt_txt").asString
            val weather = root.get("weather").asJsonArray.get(0).asJsonObject
            val description = weather.get("main").asString
            val iconId = weather.get("icon").asString
            val forecast = Forecast(dateTime, description, temperature, iconId)
            forecasts.add(forecast)
        }
        return forecasts
    }

    private fun extractWeatherFromJson(json: JsonObject): Weather {
        val country = json.get("sys")?.asJsonObject?.get("country")?.asString
        val city = json.get("name")?.asString
        val weatherJson = json.get("weather")?.asJsonArray?.get(0)?.asJsonObject
        val description = weatherJson?.get("main")?.asString
        val iconId = weatherJson?.get("icon")?.asString
        val wind = json.get("wind")?.asJsonObject
        val windSpeed = wind?.get("speed")?.asDouble
        val windDirection = wind?.get("deg")?.asDouble
        val main = json.get("main")?.asJsonObject
        val pressure = main?.get("pressure")?.asDouble
        val humidity = main?.get("humidity")?.asDouble
        val temperature = main?.get("temp")?.asDouble
        val precipitation = json.get("precipitation")?.asJsonObject?.get("value")?.asDouble
        return Weather(country, city, description, temperature, humidity, pressure, windSpeed, windDirection, precipitation, iconId)
    }
}