package com.sphephelo.weather.ui

data class X(
    val clouds: Clouds,
    val dt: Double,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)