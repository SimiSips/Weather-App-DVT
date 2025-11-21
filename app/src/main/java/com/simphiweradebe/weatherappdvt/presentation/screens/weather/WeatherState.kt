package com.simphiweradebe.weatherappdvt.presentation.screens.weather

import com.simphiweradebe.weatherappdvt.data.models.OneCallResponse

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: OneCallResponse? = null,
    val error: String = ""
)
