package com.simphiweradebe.weatherappdvt.presentation.screens.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.simphiweradebe.weatherappdvt.R
import com.simphiweradebe.weatherappdvt.utils.WeatherIconMapper
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5B9FD8),
                        Color(0xFF2E5B7A)
                    )
                )
            )
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
            state.error.isNotBlank() -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Oops!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = state.error,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Button(
                        onClick = { viewModel.retry() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.2f)
                        )
                    ) {
                        Text(
                            text = "Retry",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            state.weather != null -> {
                val weather = state.weather!!
                val current = weather.current

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    // Custom Header
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }

                            Text(
                                text = "Cuaca",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )

                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    // Date and Location
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                                Text(
                                    text = dateFormat.format(Date()),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.White
                                )

                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color.White.copy(alpha = 0.25f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(Color.Red)
                                        )
                                        Text(
                                            text = "Realtime",
                                            fontSize = 14.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    Icons.Outlined.LocationOn,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Johannesburg, South Africa",
                                    fontSize = 16.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }

                    // Large Weather Icon
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val weatherCondition = current?.weather?.firstOrNull()
                            Image(
                                painter = painterResource(
                                    id = WeatherIconMapper.getWeatherIcon(weatherCondition?.main)
                                ),
                                contentDescription = weatherCondition?.description,
                                modifier = Modifier.size(250.dp)
                            )
                        }
                    }

                    // Temperature
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${current?.temp?.roundToInt()}°C",
                                fontSize = 96.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            val weatherCondition = current?.weather?.firstOrNull()
                            Text(
                                text = weatherCondition?.description?.uppercase() ?: "",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${weather.daily?.firstOrNull()?.temp?.max?.roundToInt()}/${weather.daily?.firstOrNull()?.temp?.min?.roundToInt()} Feels Like ${current?.feelsLike?.roundToInt()}",
                                fontSize = 16.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }
                    }

                    // Wind and Humidity
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_clear_sky),
                                    contentDescription = "Wind",
                                    modifier = Modifier.size(40.dp),
                                    tint = Color.White.copy(alpha = 0.7f)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Wind",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = "${current?.windSpeed?.roundToInt()}km/h",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_rain),
                                    contentDescription = "Humidity",
                                    modifier = Modifier.size(40.dp),
                                    tint = Color.White.copy(alpha = 0.7f)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Humidity",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = "${current?.humidity}%",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    // Hourly Forecast
                    item {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Today",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Next 7 Days  >",
                                    fontSize = 16.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(24.dp),
                                color = Color.Black.copy(alpha = 0.3f)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    val weatherCondition = current?.weather?.firstOrNull()
                                    Text(
                                        text = "${weatherCondition?.main ?: "Cloudy"} - Low temperature ${weather.daily?.firstOrNull()?.temp?.min?.roundToInt()} - ${weather.daily?.firstOrNull()?.temp?.max?.roundToInt()}°C",
                                        fontSize = 14.sp,
                                        color = Color.White.copy(alpha = 0.9f),
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    )

                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                                    ) {
                                        items(weather.hourly?.take(5) ?: emptyList()) { hourly ->
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Image(
                                                    painter = painterResource(
                                                        id = WeatherIconMapper.getWeatherIcon(
                                                            hourly.weather?.firstOrNull()?.main
                                                        )
                                                    ),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(48.dp)
                                                )
                                                val time = SimpleDateFormat("hh:mm a", Locale.getDefault())
                                                    .format(Date(hourly.dt?.times(1000) ?: 0))
                                                Text(
                                                    text = time,
                                                    fontSize = 13.sp,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Spacer at bottom
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5B9FD8),
                        Color(0xFF2E5B7A)
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                    }
                    Text(text = "Cuaca", fontSize = 24.sp, fontWeight = FontWeight.Medium, color = Color.White)
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.White)
                    }
                }
            }

            // Date & Location
            item {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Apr 24, 2024", fontSize = 20.sp, color = Color.White)
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.25f)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Red))
                                Text("Realtime", fontSize = 14.sp, color = Color.White)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Outlined.LocationOn, null, tint = Color.White, modifier = Modifier.size(20.dp))
                        Text("Johannesburg, South Africa", fontSize = 16.sp, color = Color.White.copy(0.9f))
                    }
                }
            }

            // Weather Icon
            item {
                Box(modifier = Modifier.fillMaxWidth().height(280.dp), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(R.drawable.ic_partly_cloudy),
                        contentDescription = null,
                        modifier = Modifier.size(250.dp)
                    )
                }
            }

            // Temperature
            item {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("29°C", fontSize = 96.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("SUNNY CLOUDY", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = Color.White, letterSpacing = 2.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("29/22 Feels Like 18", fontSize = 16.sp, color = Color.White.copy(0.85f))
                }
            }

            // Wind & Humidity
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painterResource(R.drawable.ic_clear_sky), "Wind", modifier = Modifier.size(40.dp), tint = Color.White.copy(0.7f))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Wind", fontSize = 14.sp, color = Color.White.copy(0.7f))
                        Text("24km/h", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painterResource(R.drawable.ic_rain), "Humidity", modifier = Modifier.size(40.dp), tint = Color.White.copy(0.7f))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Humidity", fontSize = 14.sp, color = Color.White.copy(0.7f))
                        Text("50%", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                }
            }

            // Hourly Forecast
            item {
                Column {
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Today", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text("Next 7 Days  >", fontSize = 16.sp, color = Color.White.copy(0.8f))
                    }
                    Surface(shape = RoundedCornerShape(24.dp), color = Color.Black.copy(0.3f)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Sunny Cloudy - Low temperature 18 - 22°C", fontSize = 14.sp, color = Color.White.copy(0.9f), modifier = Modifier.padding(bottom = 12.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                                items(5) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Image(painterResource(R.drawable.ic_partly_cloudy), null, modifier = Modifier.size(48.dp))
                                        Text("06:00 am", fontSize = 13.sp, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
