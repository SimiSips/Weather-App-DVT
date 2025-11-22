package com.simphiweradebe.weatherappdvt.utils

import com.simphiweradebe.weatherappdvt.R

object WeatherBackgroundMapper {
    fun getWeatherBackground(condition: String?): Int {
        return when (condition?.lowercase()) {
            "clear", "clear sky" -> R.drawable.bg_sunny
            "clouds", "few clouds", "scattered clouds", "broken clouds", "overcast clouds" -> R.drawable.bg_cloudy
            "rain", "light rain", "moderate rain", "heavy rain", "shower rain" -> R.drawable.bg_rainy
            "drizzle", "light drizzle" -> R.drawable.bg_rainy
            "thunderstorm" -> R.drawable.bg_rainy
            "snow", "light snow", "heavy snow" -> R.drawable.bg_cloudy
            "mist", "fog", "haze" -> R.drawable.bg_cloudy
            else -> R.drawable.bg_sunny
        }
    }
}
