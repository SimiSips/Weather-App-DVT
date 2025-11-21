package com.simphiweradebe.weatherappdvt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.simphiweradebe.weatherappdvt.presentation.screens.weather.WeatherScreen
import com.simphiweradebe.weatherappdvt.ui.theme.WeatherAppDVTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppDVTTheme {
                WeatherScreen()
            }
        }
    }
}