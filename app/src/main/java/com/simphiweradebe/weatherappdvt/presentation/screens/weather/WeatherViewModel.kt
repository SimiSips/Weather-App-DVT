package com.simphiweradebe.weatherappdvt.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simphiweradebe.weatherappdvt.domain.repository.WeatherRepository
import com.simphiweradebe.weatherappdvt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state

    init {
        // Default location - Johannesburg
        getWeather(-26.2041, 28.0473)
    }

    fun getWeather(lat: Double, lon: Double) {
        repository.getWeatherData(lat, lon).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WeatherState(weather = result.data)
                }
                is Resource.Error -> {
                    _state.value = WeatherState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = WeatherState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
