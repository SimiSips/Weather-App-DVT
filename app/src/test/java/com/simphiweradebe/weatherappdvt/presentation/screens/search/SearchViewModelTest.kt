package com.simphiweradebe.weatherappdvt.presentation.screens.search

import app.cash.turbine.test
import com.simphiweradebe.weatherappdvt.data.models.GeoLocation
import com.simphiweradebe.weatherappdvt.domain.repository.WeatherRepository
import com.simphiweradebe.weatherappdvt.utils.Resource
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
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var weatherRepository: WeatherRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        weatherRepository = mockk(relaxed = true)
        viewModel = SearchViewModel(weatherRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchLocation should emit loading then success with locations`() = runTest {
        val mockLocations = listOf(
            GeoLocation(
                name = "London",
                lat = 51.5074,
                lon = -0.1278,
                country = "GB",
                state = null
            ),
            GeoLocation(
                name = "London",
                lat = 42.9834,
                lon = -81.2497,
                country = "CA",
                state = "Ontario"
            )
        )
        every { weatherRepository.searchLocation(any()) } returns flowOf(
            Resource.Loading(),
            Resource.Success(mockLocations)
        )

        viewModel.searchLocation("London")
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(mockLocations, state.locations)
            assertEquals(false, state.isLoading)
            assertEquals("", state.error)
            assertEquals("London", state.searchQuery)
        }
    }

    @Test
    fun `searchLocation should emit error state on failure`() = runTest {
        val errorMessage = "Network error"
        every { weatherRepository.searchLocation(any()) } returns flowOf(
            Resource.Error(errorMessage)
        )

        viewModel.searchLocation("Test")
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isLoading)
            assertTrue(state.locations.isEmpty())
        }
    }

    @Test
    fun `searchLocation should clear results if query is blank`() = runTest {
        viewModel.searchLocation("")
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.locations.isEmpty())
            assertEquals("", state.searchQuery)
            assertEquals("", state.error)
        }
    }

    @Test
    fun `searchLocation should update searchQuery`() = runTest {
        val query = "Paris"
        val mockLocations = listOf(
            GeoLocation(
                name = "Paris",
                lat = 48.8566,
                lon = 2.3522,
                country = "FR",
                state = null
            )
        )
        every { weatherRepository.searchLocation(any()) } returns flowOf(
            Resource.Success(mockLocations)
        )

        viewModel.searchLocation(query)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertEquals(query, state.searchQuery)
        }
    }

    @Test
    fun `clearSearch should reset state to initial`() = runTest {
        val mockLocations = listOf(
            GeoLocation(name = "Test", lat = 0.0, lon = 0.0, country = "US", state = null)
        )
        every { weatherRepository.searchLocation(any()) } returns flowOf(
            Resource.Success(mockLocations)
        )

        viewModel.searchLocation("Test")
        advanceUntilIdle()

        viewModel.clearSearch()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.locations.isEmpty())
            assertEquals("", state.searchQuery)
            assertEquals("", state.error)
            assertEquals(false, state.isLoading)
        }
    }
}
