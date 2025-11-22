package com.simphiweradebe.weatherappdvt.presentation.screens.forecast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.simphiweradebe.weatherappdvt.presentation.screens.forecast.components.*
import com.simphiweradebe.weatherappdvt.presentation.screens.weather.WeatherViewModel
import com.simphiweradebe.weatherappdvt.utils.WeatherBackgroundMapper
import com.simphiweradebe.weatherappdvt.utils.WeatherIconMapper
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ForecastDetailScreen(
    onBackClick: () -> Unit,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Get dynamic background based on current weather
    val backgroundRes = state.weather?.current?.weather?.firstOrNull()?.main?.let { condition ->
        WeatherBackgroundMapper.getWeatherBackground(condition)
    } ?: WeatherBackgroundMapper.getWeatherBackground("clear")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Weather background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark overlay for better text visibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(50.dp))

                // Back button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Today header
                Text(
                    text = "Today",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Date
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                Text(
                    text = dateFormat.format(Date()),
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Weather Icon
                state.weather?.current?.weather?.firstOrNull()?.let { weather ->
                    Icon(
                        painter = painterResource(id = WeatherIconMapper.getWeatherIcon(weather.main)),
                        contentDescription = weather.description,
                        modifier = Modifier.size(200.dp),
                        tint = Color.Unspecified
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Temperature
                state.weather?.current?.temp?.let { temp ->
                    Text(
                        text = "${temp.toInt()}°C",
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Weather condition
                state.weather?.current?.weather?.firstOrNull()?.let { weather ->
                    Text(
                        text = weather.main?.uppercase() ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        letterSpacing = 2.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // High/Low & Feels Like
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.weather?.daily?.firstOrNull()?.let { daily ->
                        Text(
                            text = "${daily.temp?.max?.toInt()}° / ${daily.temp?.min?.toInt()}°",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    state.weather?.current?.feelsLike?.let { feelsLike ->
                        Text(
                            text = " Feels Like ${feelsLike.toInt()}°",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Hourly Forecast Card - will be built next
                HourlyForecastCard(
                    hourlyData = state.weather?.hourly?.take(12) ?: emptyList()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Feels Like & Humidity Info Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Feels Like Card
                    state.weather?.current?.feelsLike?.let { feelsLike ->
                        MetricInfoCard(
                            modifier = Modifier.weight(1f),
                            title = "The weather feels like",
                            value = "${feelsLike.toInt()}°C",
                            icon = "🌡️"
                        )
                    }

                    // Humidity Info Card
                    HumidityDescriptionCard(
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Wind & Humidity Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    state.weather?.current?.windSpeed?.let { windSpeed ->
                        WindCard(
                            modifier = Modifier.weight(1f),
                            windSpeed = windSpeed
                        )
                    }

                    state.weather?.current?.humidity?.let { humidity ->
                        HumidityCard(
                            modifier = Modifier.weight(1f),
                            humidity = humidity
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Weather Alert Card
                WeatherAlertCard()

                Spacer(modifier = Modifier.height(24.dp))

                // Weekly Forecast Header
                Text(
                    text = "Weather forecast this week",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Start
                )

                // Weekly Forecast List
                state.weather?.daily?.take(7)?.forEachIndexed { index, daily ->
                    WeeklyForecastItem(
                        day = getDayName(index),
                        weatherCondition = daily.weather?.firstOrNull()?.main ?: "",
                        icon = WeatherIconMapper.getWeatherIcon(daily.weather?.firstOrNull()?.main),
                        highTemp = daily.temp?.max?.toInt() ?: 0,
                        lowTemp = daily.temp?.min?.toInt() ?: 0
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

fun getDayName(index: Int): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, index)
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    return dayFormat.format(calendar.time)
}

@Preview(showBackground = true)
@Composable
fun ForecastDetailScreenPreview() {
    ForecastDetailScreen(onBackClick = {})
}
