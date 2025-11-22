package com.simphiweradebe.weatherappdvt.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simphiweradebe.weatherappdvt.domain.repository.WeatherRepository
import com.simphiweradebe.weatherappdvt.utils.LocationManager
import com.simphiweradebe.weatherappdvt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationManager: LocationManager
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state

    private var lastLat: Double = -26.2041
    private var lastLon: Double = 28.0473
    private var lastCityName: String = "Johannesburg, South Africa"

    init {
        // Try to get current location weather, fallback to default
        getCurrentLocationWeather()
    }

    fun getCurrentLocationWeather() {
        viewModelScope.launch {
            if (locationManager.hasLocationPermission()) {
                val location = locationManager.getCurrentLocation()
                if (location != null) {
                    getWeather(location.latitude, location.longitude, "Current Location")
                } else {
                    // Fallback to default location
                    getWeather(lastLat, lastLon, lastCityName)
                }
            } else {
                // No permission, use default location
                getWeather(lastLat, lastLon, lastCityName)
            }
        }
    }

    fun getWeather(lat: Double, lon: Double, cityName: String = "Unknown Location") {
        lastLat = lat
        lastLon = lon
        lastCityName = cityName

        repository.getWeatherData(lat, lon).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WeatherState(
                        weather = result.data,
                        cityName = cityName
                    )
                }
                is Resource.Error -> {
                    _state.value = WeatherState(
                        error = result.message ?: "An unexpected error occurred",
                        cityName = cityName
                    )
                }
                is Resource.Loading -> {
                    _state.value = WeatherState(
                        isLoading = true,
                        cityName = cityName
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun retry() {
        getWeather(lastLat, lastLon, lastCityName)
    }
}
