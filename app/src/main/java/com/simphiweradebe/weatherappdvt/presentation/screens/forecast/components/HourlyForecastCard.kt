package com.simphiweradebe.weatherappdvt.presentation.screens.forecast.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simphiweradebe.weatherappdvt.data.models.HourlyWeather
import com.simphiweradebe.weatherappdvt.utils.WeatherIconMapper
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HourlyForecastCard(
    hourlyData: List<HourlyWeather>
) {
    if (hourlyData.isEmpty()) return

    val minTemp = hourlyData.mapNotNull { it.temp }.minOrNull() ?: 0.0
    val maxTemp = hourlyData.mapNotNull { it.temp }.maxOrNull() ?: 30.0

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF2A3F5F)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(
                            id = WeatherIconMapper.getWeatherIcon(
                                hourlyData.firstOrNull()?.weather?.firstOrNull()?.main
                            )
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sunny Cloudy - Low temperature ${minTemp.toInt()} - ${maxTemp.toInt()}°C",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Temperature Graph
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                // Draw the temperature curves
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val width = size.width
                    val height = size.height
                    val points = hourlyData.take(5)
                    val spacing = width / (points.size - 1)

                    // High temperature line (top wavy line - blue)
                    val highTempPath = Path()
                    points.forEachIndexed { index, hourly ->
                        val x = index * spacing
                        val normalizedTemp = ((hourly.temp ?: 0.0) - minTemp) / (maxTemp - minTemp)
                        val y = height * 0.3f - (normalizedTemp.toFloat() * height * 0.2f)

                        if (index == 0) {
                            highTempPath.moveTo(x, y)
                        } else {
                            highTempPath.lineTo(x, y)
                        }
                    }

                    drawPath(
                        path = highTempPath,
                        color = Color(0xFF4A9FE8),
                        style = Stroke(width = 4.dp.toPx())
                    )

                    // Low temperature line (bottom dashed line - gray)
                    val lowTempPath = Path()
                    points.forEachIndexed { index, hourly ->
                        val x = index * spacing
                        val normalizedTemp = ((hourly.temp ?: 0.0) - minTemp) / (maxTemp - minTemp)
                        val y = height * 0.7f - (normalizedTemp.toFloat() * height * 0.2f)

                        if (index == 0) {
                            lowTempPath.moveTo(x, y)
                        } else {
                            lowTempPath.lineTo(x, y)
                        }
                    }

                    drawPath(
                        path = lowTempPath,
                        color = Color.Gray.copy(alpha = 0.5f),
                        style = Stroke(width = 3.dp.toPx())
                    )

                    // Draw point marker at middle (12:00 PM typically)
                    val midIndex = 2
                    if (midIndex < points.size) {
                        val x = midIndex * spacing
                        val normalizedTemp = ((points[midIndex].temp ?: 0.0) - minTemp) / (maxTemp - minTemp)
                        val y = height * 0.3f - (normalizedTemp.toFloat() * height * 0.2f)

                        drawCircle(
                            color = Color.White,
                            radius = 8.dp.toPx(),
                            center = Offset(x, y)
                        )

                        drawCircle(
                            color = Color(0xFF4A9FE8),
                            radius = 5.dp.toPx(),
                            center = Offset(x, y)
                        )
                    }
                }

                // Temperature label at 12:00 PM
                if (hourlyData.size > 2) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(y = (-10).dp)
                            .background(
                                Color(0xFF3BA4E8),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${hourlyData[2].temp?.toInt()}/${maxTemp.toInt()}°",
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Time slots and icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                hourlyData.take(5).forEach { hourly ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(60.dp)
                    ) {
                        // Time
                        val timeFormat = SimpleDateFormat("hh:00 a", Locale.getDefault())
                        val time = Date((hourly.dt ?: 0) * 1000L)
                        Text(
                            text = timeFormat.format(time),
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Weather icon
                        Icon(
                            painter = painterResource(
                                id = WeatherIconMapper.getWeatherIcon(
                                    hourly.weather?.firstOrNull()?.main
                                )
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}
