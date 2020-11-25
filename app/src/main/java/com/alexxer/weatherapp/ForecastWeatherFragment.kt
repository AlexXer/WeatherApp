package com.alexxer.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexxer.weatherapp.data.models.Forecast
import com.alexxer.weatherapp.data.models.RetrofitInstance
import com.alexxer.weatherapp.data.models.Weather
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForecastWeatherFragment : Fragment() {

    val forecasts: ArrayList<Forecast> = ArrayList<Forecast>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val request =
            RetrofitInstance.api.getForecastWeather(53.9, 37.57, "89397e33eaecc4a607d8e7436c770ba8")
        val disposable = request
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ json ->
                val array = json.get("list")?.asJsonArray
                array?.forEach {
                    val temperature = it.asJsonObject.get("main").asJsonObject.get("temp").asDouble
                    val description =
                        it.asJsonObject.get("weather").asJsonArray.get(0).asJsonObject.get("main").asString
                    val dateTime = it.asJsonObject.get("dt_txt").asString
                    val forecast = Forecast(dateTime, description, temperature)
                    forecasts.add(forecast)
                    println(forecast.toString())
                    println("----------------------------------------------------")
                }
                println("OKI4")
            }) { it.printStackTrace() }

        return inflater.inflate(R.layout.fragment_forecast_weather, container, false)
    }

}