package com.simphiweradebe.weatherappdvt.data.repository

import com.simphiweradebe.weatherappdvt.data.models.GeoLocation
import com.simphiweradebe.weatherappdvt.data.models.OneCallResponse
import com.simphiweradebe.weatherappdvt.data.remote.WeatherApi
import com.simphiweradebe.weatherappdvt.domain.repository.WeatherRepository
import com.simphiweradebe.weatherappdvt.utils.Constants
import com.simphiweradebe.weatherappdvt.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override fun getWeatherData(lat: Double, lon: Double): Flow<Resource<OneCallResponse>> = flow {
        try {
            emit(Resource.Loading())
            val weather = api.getWeatherData(
                lat = lat,
                lon = lon,
                apiKey = Constants.API_KEY
            )
            emit(Resource.Success(weather))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = e.localizedMessage ?: "An unexpected error occurred"
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server. Check your internet connection."
            ))
        }
    }

    override fun searchLocation(query: String): Flow<Resource<List<GeoLocation>>> = flow {
        try {
            emit(Resource.Loading())
            val locations = api.searchLocation(
                query = query,
                apiKey = Constants.API_KEY
            )
            emit(Resource.Success(locations))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = e.localizedMessage ?: "An unexpected error occurred"
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server. Check your internet connection."
            ))
        }
    }
}
