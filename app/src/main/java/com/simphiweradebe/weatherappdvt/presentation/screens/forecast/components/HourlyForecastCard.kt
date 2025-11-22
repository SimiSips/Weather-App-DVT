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
import androidx.compose.ui.graphics.*
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
                    .height(140.dp)
            ) {
                // Draw the temperature curves
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val width = size.width
                    val height = size.height
                    val points = hourlyData.take(8)
                    if (points.size < 2) return@Canvas

                    val spacing = width / (points.size - 1).coerceAtLeast(1)
                    val padding = 40f

                    val curvePath = Path()
                    val fillPath = Path()

                    val curvePoints = points.mapIndexed { index, hourly ->
                        val x = index * spacing
                        val temp = hourly.temp ?: minTemp
                        val normalizedTemp = if (maxTemp > minTemp) {
                            ((temp - minTemp) / (maxTemp - minTemp)).coerceIn(0.0, 1.0)
                        } else {
                            0.5
                        }
                        val y = height - padding - (normalizedTemp.toFloat() * (height - 2 * padding))
                        Offset(x, y)
                    }

                    curvePath.moveTo(curvePoints[0].x, curvePoints[0].y)
                    fillPath.moveTo(curvePoints[0].x, height)
                    fillPath.lineTo(curvePoints[0].x, curvePoints[0].y)

                    for (i in 1 until curvePoints.size) {
                        val prev = curvePoints[i - 1]
                        val current = curvePoints[i]

                        val controlX = (prev.x + current.x) / 2f
                        val controlY = (prev.y + current.y) / 2f

                        curvePath.quadraticBezierTo(
                            prev.x, prev.y,
                            controlX, controlY
                        )
                        fillPath.quadraticBezierTo(
                            prev.x, prev.y,
                            controlX, controlY
                        )
                    }

                    val lastPoint = curvePoints.last()
                    curvePath.lineTo(lastPoint.x, lastPoint.y)
                    fillPath.lineTo(lastPoint.x, lastPoint.y)

                    fillPath.lineTo(lastPoint.x, height)
                    fillPath.close()

                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF4A9FE8).copy(alpha = 0.4f),
                                Color(0xFF4A9FE8).copy(alpha = 0.05f)
                            )
                        )
                    )

                    drawPath(
                        path = curvePath,
                        color = Color(0xFF4A9FE8),
                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                    )

                    curvePoints.forEachIndexed { index, point ->
                        drawCircle(
                            color = Color.White,
                            radius = 6.dp.toPx(),
                            center = point
                        )
                        drawCircle(
                            color = Color(0xFF4A9FE8),
                            radius = 3.dp.toPx(),
                            center = point
                        )
                    }
                }

                if (hourlyData.isNotEmpty()) {
                    val maxTempIndex = hourlyData.take(8).indexOfFirst { it.temp == maxTemp }
                    if (maxTempIndex >= 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .offset(
                                    x = ((maxTempIndex.toFloat() / 7.coerceAtLeast(1)) * 100 - 50).dp,
                                    y = 5.dp
                                )
                                .background(
                                    Color(0xFF3BA4E8),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${maxTemp.toInt()}°",
                                fontSize = 13.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Time slots and icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                hourlyData.take(8).forEach { hourly ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Time
                        val timeFormat = SimpleDateFormat("ha", Locale.getDefault())
                        val time = Date((hourly.dt ?: 0) * 1000L)
                        Text(
                            text = timeFormat.format(time),
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Weather icon
                        Icon(
                            painter = painterResource(
                                id = WeatherIconMapper.getWeatherIcon(
                                    hourly.weather?.firstOrNull()?.main
                                )
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}
