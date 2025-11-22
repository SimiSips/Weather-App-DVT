package com.simphiweradebe.weatherappdvt.data.remote

import com.simphiweradebe.weatherappdvt.data.models.GeoLocation
import com.simphiweradebe.weatherappdvt.data.models.OneCallResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherApi {

    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String? = null
    ): OneCallResponse

    @GET
    suspend fun searchLocation(
        @Url url: String = "https://api.openweathermap.org/geo/1.0/direct",
        @Query("q") query: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<GeoLocation>
}
