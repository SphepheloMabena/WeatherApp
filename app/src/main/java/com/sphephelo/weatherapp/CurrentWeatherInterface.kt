package com.sphephelo.weather.ui

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

const val base_urll="https://api.openweathermap.org/"
//val new_key="api.openweathermap.org/data/2.5/forecast?q=Sandton&appid=5e47fbfc539ce68d6359b96c12d87c75"
interface CurrentWeatherInterface {
    @GET
    fun CurrentWeatherInfom(@Url url:String):retrofit2.Call<Current>


}

object CurrentWeatherInstance
{
    val currentWeatherInterface:CurrentWeatherInterface
    init {
        val retrofit= Retrofit.Builder()
                .baseUrl(base_urll)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        currentWeatherInterface=retrofit.create(CurrentWeatherInterface::class.java)


    }
}