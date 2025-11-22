package com.simphiweradebe.weatherappdvt.navigation

sealed class Screen(val route: String) {
    object Weather : Screen("weather")
    object ForecastDetail : Screen("forecast_detail")
}
