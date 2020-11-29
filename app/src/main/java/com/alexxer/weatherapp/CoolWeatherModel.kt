package com.alexxer.weatherapp

import android.content.Context
import com.alexxer.weatherapp.data.models.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.security.auth.Subject

class CoolWeatherModel(context: Context) {
    private val weatherSubject: PublishSubject<Weather> = PublishSubject.create<Weather>()
    private val forecastSubject: BehaviorSubject<List<Forecast>> = BehaviorSubject.create<List<Forecast>>()
    private val updateDisposable: CompositeDisposable = CompositeDisposable()
    private val complexWeatherModel: ComplexWeatherModel = ComplexWeatherModel(CachedWeatherModel(context), RemoteWeatherModel())

    private var lastGeoLocation: GeoLocation? = null

    fun getWeatherUpdate(): Observable<Weather> {
        return weatherSubject
    }

    fun getForecastUpdate(): Observable<List<Forecast>> {
        return forecastSubject
    }

    fun setLocation(geoLocation: GeoLocation) {
        if (lastGeoLocation?.distanceTo(geoLocation) ?: 5001.0 > 5000) {
            lastGeoLocation = geoLocation

            updateDisposable.clear()//прекращение подписки
            val d1 = complexWeatherModel
                .getCurrentWeather(geoLocation.latitude, geoLocation.longitude)
                .subscribe({ weatherSubject.onNext(it) }) { weatherSubject.onError(it) }
            val d2 = complexWeatherModel
                .getForecastWeather(geoLocation.latitude, geoLocation.longitude)
                .subscribe({ forecastSubject.onNext(it) }) { forecastSubject.onError(it) }
            updateDisposable.addAll(d1, d2)// контейнер для всех потоков
        }
    }
}