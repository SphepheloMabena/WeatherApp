package com.sphephelo.weather.ui

data class WeatherInfo(
    val city: City,
    val cnt: Double,
    val cod: String,
    val list: List<X>,
    val message: Double
)