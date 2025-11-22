package com.simphiweradebe.weatherappdvt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.simphiweradebe.weatherappdvt.navigation.NavGraph
import com.simphiweradebe.weatherappdvt.ui.theme.WeatherAppDVTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppDVTTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}