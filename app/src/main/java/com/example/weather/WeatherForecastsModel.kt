package com.example.weather

import com.google.gson.annotations.SerializedName
import java.util.Date


data class WeatherForecastsHourly(
    @SerializedName("WeatherForecasts")
    val WeatherForcasts: List<WeatherForcasts>)

data class WeatherForcasts(
    @SerializedName("location")
    val location : Location,
    @SerializedName("forecasts")
    val Forecasts: List<Forecast>)

data class Location(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double)

data class Forecast(
    @SerializedName("time")
    var Time: Date,
    @SerializedName("data")
    val data: Data)

data class Data(
    @SerializedName("cond")
    val cond: Int,
    @SerializedName("rh")
    val rc: Double,
    @SerializedName("tc")
    val tc: Double
)