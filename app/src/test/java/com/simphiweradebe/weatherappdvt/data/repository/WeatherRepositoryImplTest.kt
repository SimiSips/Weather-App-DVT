package com.simphiweradebe.weatherappdvt.data.repository

import app.cash.turbine.test
import com.simphiweradebe.weatherappdvt.data.models.*
import com.simphiweradebe.weatherappdvt.data.remote.WeatherApi
import com.simphiweradebe.weatherappdvt.utils.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

class WeatherRepositoryImplTest {

    private lateinit var repository: WeatherRepositoryImpl
    private lateinit var api: WeatherApi

    @Before
    fun setup() {
        api = mockk()
        repository = WeatherRepositoryImpl(api)
    }

    @Test
    fun `getWeatherData should emit loading then success`() = runTest {
        val mockWeatherData = createMockWeatherData()
        coEvery { api.getWeatherData(any(), any(), any(), any(), any()) } returns mockWeatherData

        repository.getWeatherData(10.0, 20.0).test {
            val loadingState = awaitItem()
            assertTrue(loadingState is Resource.Loading)

            val successState = awaitItem()
            assertTrue(successState is Resource.Success)
            assertEquals(mockWeatherData, successState.data)

            awaitComplete()
        }
    }

    @Test
    fun `getWeatherData should emit error on IOException`() = runTest {
        coEvery { api.getWeatherData(any(), any(), any(), any(), any()) } throws IOException()

        repository.getWeatherData(10.0, 20.0).test {
            awaitItem()

            val errorState = awaitItem()
            assertTrue(errorState is Resource.Error)
            assertTrue(errorState.message?.contains("Couldn't reach server") == true)

            awaitComplete()
        }
    }

    @Test
    fun `getWeatherData should emit error on HttpException`() = runTest {
        val httpException = mockk<HttpException>(relaxed = true)
        every { httpException.localizedMessage } returns "Not Found"
        coEvery { api.getWeatherData(any(), any(), any(), any(), any()) } throws httpException

        repository.getWeatherData(10.0, 20.0).test {
            awaitItem()

            val errorState = awaitItem()
            assertTrue(errorState is Resource.Error)

            awaitComplete()
        }
    }

    @Test
    fun `searchLocation should emit success with location list`() = runTest {
        val mockLocations = listOf(
            GeoLocation(
                name = "New York",
                lat = 40.7128,
                lon = -74.0060,
                country = "US",
                state = "New York"
            )
        )
        coEvery { api.searchLocation(any(), any(), any(), any()) } returns mockLocations

        repository.searchLocation("New York").test {
            awaitItem()

            val successState = awaitItem()
            assertTrue(successState is Resource.Success)
            assertEquals(mockLocations, successState.data)

            awaitComplete()
        }
    }

    @Test
    fun `searchLocation should emit error on network failure`() = runTest {
        coEvery { api.searchLocation(any(), any(), any(), any()) } throws IOException()

        repository.searchLocation("Test").test {
            awaitItem()

            val errorState = awaitItem()
            assertTrue(errorState is Resource.Error)

            awaitComplete()
        }
    }

    private fun createMockWeatherData(): OneCallResponse {
        return OneCallResponse(
            lat = 10.0,
            lon = 20.0,
            timezone = "UTC",
            current = CurrentWeather(
                dt = 1234567890,
                temp = 25.0,
                feelsLike = 24.0,
                humidity = 60,
                windSpeed = 5.0,
                weather = listOf(
                    WeatherCondition(
                        id = 800,
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d"
                    )
                )
            ),
            hourly = null,
            daily = null,
            alerts = null
        )
    }
}
