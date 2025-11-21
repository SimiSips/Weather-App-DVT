package com.simphiweradebe.weatherappdvt.utils

import com.simphiweradebe.weatherappdvt.R

object WeatherIconMapper {
    fun getWeatherIcon(condition: String?): Int {
        return when (condition?.lowercase()) {
            "clear", "clear sky" -> R.drawable.ic_clear_sky
            "few clouds", "scattered clouds", "partly cloudy" -> R.drawable.ic_partly_cloudy
            "broken clouds", "overcast clouds", "clouds" -> R.drawable.ic_cloudy
            "shower rain", "rain", "light rain", "moderate rain" -> R.drawable.ic_rain
            "drizzle", "light intensity drizzle" -> R.drawable.ic_drizzle
            "thunderstorm" -> R.drawable.ic_thunderstorm
            "snow", "light snow", "heavy snow" -> R.drawable.ic_snow
            "mist", "fog", "haze" -> R.drawable.ic_fog
            else -> R.drawable.ic_partly_cloudy
        }
    }
}
