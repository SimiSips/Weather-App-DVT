package com.simphiweradebe.weatherappdvt.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simphiweradebe.weatherappdvt.presentation.screens.forecast.ForecastDetailScreen
import com.simphiweradebe.weatherappdvt.presentation.screens.weather.WeatherScreen
import com.simphiweradebe.weatherappdvt.presentation.screens.weather.WeatherViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    // Hoist ViewModel to NavGraph level so both screens share the same instance
    val weatherViewModel: WeatherViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Weather.route
    ) {
        composable(route = Screen.Weather.route) {
            WeatherScreen(
                onNavigateToForecast = {
                    navController.navigate(Screen.ForecastDetail.route)
                },
                viewModel = weatherViewModel
            )
        }

        composable(route = Screen.ForecastDetail.route) {
            ForecastDetailScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                viewModel = weatherViewModel
            )
        }
    }
}
