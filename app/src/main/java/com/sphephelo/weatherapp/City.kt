package com.sphephelo.weather.ui

data class City(
    val coord: Coord,
    val country: String,
    val id: Double,
    val name: String,
    val population: Double,
    val sunrise: Double,
    val sunset: Double,
    val timezone:Double
)