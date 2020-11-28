package com.alexxer.weatherapp.data.models

import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoteWeatherModel : WeatherModel {
    override fun getCurrentWeather(lat: Double, lon: Double): Single<Weather> {
        return RetrofitInstance
            .api
            .getCurrentWeather(lat, lon)
            .subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
            .map { json -> extractWeatherFromJson(json) }
    }

    override fun getForecastWeather(lat: Double, lon: Double): Single<List<Forecast>> {
        return RetrofitInstance
            .api
            .getForecastWeather(lat, lon)
            .subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
            .map { json -> extractForecastFromJson(json) }
    }

    private fun extractForecastFromJson(json: JsonObject): ArrayList<Forecast> {
        val forecasts = arrayListOf<Forecast>()
        val array = json.get("list")?.asJsonArray
        array?.forEach {
            val temperature = it.asJsonObject.get("main").asJsonObject.get("temp").asDouble
            val description =
                it.asJsonObject.get("weather").asJsonArray.get(0).asJsonObject.get("main").asString
            val dateTime = it.asJsonObject.get("dt_txt").asString
            val iconId =
                json.get("weather")?.asJsonArray?.get(0)?.asJsonObject?.get("icon")?.asString
            val forecast = Forecast(dateTime, description, temperature,iconId)
            forecasts.add(forecast)
        }
        return forecasts
    }

    private fun extractWeatherFromJson(json: JsonObject): Weather {
        val country = json.get("sys")?.asJsonObject?.get("country")?.asString
        val city = json.get("name")?.asString
        val description =
            json.get("weather")?.asJsonArray?.get(0)?.asJsonObject?.get("main")?.asString
        val iconId =
            json.get("weather")?.asJsonArray?.get(0)?.asJsonObject?.get("icon")?.asString
        val windSpeed = json.get("wind")?.asJsonObject?.get("speed")?.asDouble
        val windDirection = json.get("wind")?.asJsonObject?.get("deg")?.asDouble
        val pressure = json.get("main")?.asJsonObject?.get("pressure")?.asDouble
        val humidity = json.get("main")?.asJsonObject?.get("humidity")?.asDouble
        val temperature = json.get("main")?.asJsonObject?.get("temp")?.asDouble
        val precipitation = json.get("precipitation")?.asJsonObject?.get("value")?.asDouble
        val weather =
            Weather(
                country,
                city,
                description,
                temperature,
                humidity,
                pressure,
                windSpeed,
                windDirection,
                precipitation,
                iconId
            )
        return weather
    }
}