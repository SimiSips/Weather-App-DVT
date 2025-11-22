package com.simphiweradebe.weatherappdvt.presentation.screens.weather

import android.location.Location
import app.cash.turbine.test
import com.simphiweradebe.weatherappdvt.data.models.*
import com.simphiweradebe.weatherappdvt.domain.repository.WeatherRepository
import com.simphiweradebe.weatherappdvt.utils.LocationManager
import com.simphiweradebe.weatherappdvt.utils.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var locationManager: LocationManager
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        weatherRepository = mockk(relaxed = true)
        locationManager = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getWeather should emit loading then success state`() = runTest {
        val mockWeatherData = createMockWeatherData()
        every { weatherRepository.getWeatherData(any(), any()) } returns flowOf(
            Resource.Loading(),
            Resource.Success(mockWeatherData)
        )
        every { locationManager.hasLocationPermission() } returns false

        viewModel = WeatherViewModel(weatherRepository, locationManager)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(mockWeatherData, state.weather)
            assertEquals("", state.error)
            assertEquals(false, state.isLoading)
        }
    }

    @Test
    fun `getWeather should emit error state on failure`() = runTest {
        val errorMessage = "Network error"
        every { weatherRepository.getWeatherData(any(), any()) } returns flowOf(
            Resource.Error(errorMessage)
        )
        every { locationManager.hasLocationPermission() } returns false

        viewModel = WeatherViewModel(weatherRepository, locationManager)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertTrue(state.weather == null)
        }
    }

    @Test
    fun `getCurrentLocationWeather should get weather for current location when permission granted`() = runTest {
        val mockLocation = mockk<Location>()
        every { mockLocation.latitude } returns 10.0
        every { mockLocation.longitude } returns 20.0
        every { locationManager.hasLocationPermission() } returns true
        coEvery { locationManager.getCurrentLocation() } returns mockLocation

        val mockWeatherData = createMockWeatherData()
        every { weatherRepository.getWeatherData(any(), any()) } returns flowOf(
            Resource.Success(mockWeatherData)
        )

        viewModel = WeatherViewModel(weatherRepository, locationManager)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals("Current Location", state.cityName)
            assertEquals(mockWeatherData, state.weather)
        }
    }

    @Test
    fun `retry should call getWeather with last known coordinates`() = runTest {
        val mockWeatherData = createMockWeatherData()
        every { weatherRepository.getWeatherData(any(), any()) } returns flowOf(
            Resource.Success(mockWeatherData)
        )
        every { locationManager.hasLocationPermission() } returns false

        viewModel = WeatherViewModel(weatherRepository, locationManager)
        advanceUntilIdle()

        viewModel.retry()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(mockWeatherData, state.weather)
        }
    }

    @Test
    fun `getWeather should update city name correctly`() = runTest {
        val cityName = "New York"
        val mockWeatherData = createMockWeatherData()
        every { weatherRepository.getWeatherData(any(), any()) } returns flowOf(
            Resource.Success(mockWeatherData)
        )
        every { locationManager.hasLocationPermission() } returns false

        viewModel = WeatherViewModel(weatherRepository, locationManager)
        advanceUntilIdle()
        viewModel.getWeather(40.7128, -74.0060, cityName)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(cityName, state.cityName)
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
            hourly = listOf(
                HourlyWeather(
                    dt = 1234567890,
                    temp = 25.0,
                    weather = listOf(
                        WeatherCondition(
                            id = 800,
                            main = "Clear",
                            description = "clear sky",
                            icon = "01d"
                        )
                    )
                )
            ),
            daily = listOf(
                DailyWeather(
                    dt = 1234567890,
                    temp = Temperature(
                        day = 25.0,
                        min = 20.0,
                        max = 30.0,
                        night = 22.0,
                        eve = 24.0,
                        morn = 21.0
                    ),
                    weather = listOf(
                        WeatherCondition(
                            id = 800,
                            main = "Clear",
                            description = "clear sky",
                            icon = "01d"
                        )
                    )
                )
            ),
            alerts = null
        )
    }
}
