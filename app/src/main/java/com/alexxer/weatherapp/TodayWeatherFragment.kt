package com.alexxer.weatherapp

import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexxer.weatherapp.data.models.*
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
        var complexWeatherModel = ComplexWeatherModel(
            CachedWeatherModel(requireContext()),
            RemoteWeatherModel()
        )

        complexWeatherModel
            .getCurrentWeather(37.421998333333335, -122.08400000000002)
            .subscribe({ println(it) }){it.printStackTrace()}

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_weather, container, false)
    }


}