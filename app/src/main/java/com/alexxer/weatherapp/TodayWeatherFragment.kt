package com.alexxer.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexxer.weatherapp.data.models.RetrofitInstance
import com.alexxer.weatherapp.data.models.Weather
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TodayWeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val request =
            RetrofitInstance.api.getCurrentWeather(53.9, 37.57, "89397e33eaecc4a607d8e7436c770ba8")
        val disposable = request
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ json ->
                val country = json.get("sys")?.asJsonObject?.get("country")?.asString
                val city = json.get("name")?.asString
                val description =
                    json.get("weather")?.asJsonArray?.get(0)?.asJsonObject?.get("main")?.asString
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
                        precipitation
                    )
                println(weather.toString())
            }) { it.printStackTrace() }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_weather, container, false)
    }


}