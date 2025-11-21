package com.simphiweradebe.weatherappdvt.domain.repository

import com.simphiweradebe.weatherappdvt.data.models.GeoLocation
import com.simphiweradebe.weatherappdvt.data.models.OneCallResponse
import com.simphiweradebe.weatherappdvt.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherData(lat: Double, lon: Double): Flow<Resource<OneCallResponse>>
    fun searchLocation(query: String): Flow<Resource<List<GeoLocation>>>
}
