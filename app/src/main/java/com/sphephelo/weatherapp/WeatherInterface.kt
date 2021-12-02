package com.sphephelo.weather.ui



import android.telecom.Call
import com.sphephelo.weatherapp.latitude
import com.sphephelo.weatherapp.longitude
import com.sphephelo.weatherapp.msg

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

val lat:Double= latitude;
val lon:Double= longitude;

object Constants {

     val lat:Double= latitude;
    val lon:Double= longitude;

}


const val base_url="https://api.openweathermap.org/"
val new_key="https://api.openweathermap.org/api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=94b7c8421c8f5599bc09de7cdd301ca1"
val annotation:String="data/2.5/forecast?lat=${lat}&lon=${lon}&appid=94b7c8421c8f5599bc09de7cdd301ca1"
interface WeatherInterface {
    @GET

    fun WeatherInfom(@Url url:String):retrofit2.Call<WeatherInfo>


}


object WeatherInstance
{
    val weatherInterface:WeatherInterface
    init {
        println("App staus : $msg")
        val retrofit= Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherInterface=retrofit.create(WeatherInterface::class.java)


    }
}