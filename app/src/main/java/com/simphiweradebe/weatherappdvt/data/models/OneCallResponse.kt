package com.simphiweradebe.weatherappdvt.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneCallResponse(
    @SerialName("lat") val lat: Double? = null,
    @SerialName("lon") val lon: Double? = null,
    @SerialName("timezone") val timezone: String? = null,
    @SerialName("timezone_offset") val timezoneOffset: Int? = null,
    @SerialName("current") val current: CurrentWeather? = null,
    @SerialName("minutely") val minutely: List<MinutelyWeather>? = null,
    @SerialName("hourly") val hourly: List<HourlyWeather>? = null,
    @SerialName("daily") val daily: List<DailyWeather>? = null,
    @SerialName("alerts") val alerts: List<WeatherAlert>? = null
)

@Serializable
data class CurrentWeather(
    @SerialName("dt") val dt: Long? = null,
    @SerialName("sunrise") val sunrise: Long? = null,
    @SerialName("sunset") val sunset: Long? = null,
    @SerialName("temp") val temp: Double? = null,
    @SerialName("feels_like") val feelsLike: Double? = null,
    @SerialName("pressure") val pressure: Int? = null,
    @SerialName("humidity") val humidity: Int? = null,
    @SerialName("dew_point") val dewPoint: Double? = null,
    @SerialName("uvi") val uvi: Double? = null,
    @SerialName("clouds") val clouds: Int? = null,
    @SerialName("visibility") val visibility: Int? = null,
    @SerialName("wind_speed") val windSpeed: Double? = null,
    @SerialName("wind_deg") val windDeg: Int? = null,
    @SerialName("wind_gust") val windGust: Double? = null,
    @SerialName("weather") val weather: List<WeatherCondition>? = null
)

@Serializable
data class MinutelyWeather(
    @SerialName("dt") val dt: Long? = null,
    @SerialName("precipitation") val precipitation: Double? = null
)

@Serializable
data class HourlyWeather(
    @SerialName("dt") val dt: Long? = null,
    @SerialName("temp") val temp: Double? = null,
    @SerialName("feels_like") val feelsLike: Double? = null,
    @SerialName("pressure") val pressure: Int? = null,
    @SerialName("humidity") val humidity: Int? = null,
    @SerialName("dew_point") val dewPoint: Double? = null,
    @SerialName("uvi") val uvi: Double? = null,
    @SerialName("clouds") val clouds: Int? = null,
    @SerialName("visibility") val visibility: Int? = null,
    @SerialName("wind_speed") val windSpeed: Double? = null,
    @SerialName("wind_deg") val windDeg: Int? = null,
    @SerialName("wind_gust") val windGust: Double? = null,
    @SerialName("weather") val weather: List<WeatherCondition>? = null,
    @SerialName("pop") val pop: Double? = null
)

@Serializable
data class DailyWeather(
    @SerialName("dt") val dt: Long? = null,
    @SerialName("sunrise") val sunrise: Long? = null,
    @SerialName("sunset") val sunset: Long? = null,
    @SerialName("moonrise") val moonrise: Long? = null,
    @SerialName("moonset") val moonset: Long? = null,
    @SerialName("moon_phase") val moonPhase: Double? = null,
    @SerialName("summary") val summary: String? = null,
    @SerialName("temp") val temp: Temperature? = null,
    @SerialName("feels_like") val feelsLike: FeelsLike? = null,
    @SerialName("pressure") val pressure: Int? = null,
    @SerialName("humidity") val humidity: Int? = null,
    @SerialName("dew_point") val dewPoint: Double? = null,
    @SerialName("wind_speed") val windSpeed: Double? = null,
    @SerialName("wind_deg") val windDeg: Int? = null,
    @SerialName("wind_gust") val windGust: Double? = null,
    @SerialName("weather") val weather: List<WeatherCondition>? = null,
    @SerialName("clouds") val clouds: Int? = null,
    @SerialName("pop") val pop: Double? = null,
    @SerialName("rain") val rain: Double? = null,
    @SerialName("uvi") val uvi: Double? = null
)

@Serializable
data class Temperature(
    @SerialName("day") val day: Double? = null,
    @SerialName("min") val min: Double? = null,
    @SerialName("max") val max: Double? = null,
    @SerialName("night") val night: Double? = null,
    @SerialName("eve") val eve: Double? = null,
    @SerialName("morn") val morn: Double? = null
)

@Serializable
data class FeelsLike(
    @SerialName("day") val day: Double? = null,
    @SerialName("night") val night: Double? = null,
    @SerialName("eve") val eve: Double? = null,
    @SerialName("morn") val morn: Double? = null
)

@Serializable
data class WeatherCondition(
    @SerialName("id") val id: Int? = null,
    @SerialName("main") val main: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("icon") val icon: String? = null
)

@Serializable
data class WeatherAlert(
    @SerialName("sender_name") val senderName: String? = null,
    @SerialName("event") val event: String? = null,
    @SerialName("start") val start: Long? = null,
    @SerialName("end") val end: Long? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("tags") val tags: List<String>? = null
)
