package com.alexxer.weatherapp

import android.app.Application
import com.alexxer.weatherapp.data.model.CoolWeatherModel

class App : Application() {
    val coolWeatherModel: CoolWeatherModel by lazy { CoolWeatherModel(this) }
}