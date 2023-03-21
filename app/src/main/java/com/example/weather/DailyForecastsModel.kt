package com.example.weather

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyForecastsModel (
        @SerializedName("WeatherForecasts")
        val DailyWeatherForcasts: List<DailyWeatherForcasts>)

    data class DailyWeatherForcasts(
        @SerializedName("location")
        val Dailylocation : DailyLocation,
        @SerializedName("forecasts")
        val DailyForecasts: List<DailyForecast>)

    data class DailyLocation(
        @SerializedName("lat")
        val Dailylat: Double,
        @SerializedName("lon")
        val Dailylon: Double)

    data class DailyForecast(
        @SerializedName("time")
        var DailyTime: Date,
        @SerializedName("data")
        val Dailydata: DailyData)

    data class DailyData(
        @SerializedName("cond")
        val cond: Int,
        @SerializedName("tc_min")
        val mintc: Double,
        @SerializedName("tc_max")
        val maxtc: Double
    )