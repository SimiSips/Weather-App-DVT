package com.simphiweradebe.weatherappdvt.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simphiweradebe.weatherappdvt.R

@Composable
fun WeatherCard(
    temperature: String,
    condition: String,
    highTemp: String,
    lowTemp: String,
    @DrawableRes iconRes: Int,
    date: String = "",
    location: String = "",
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF5B9FD8),
                                Color(0xFF4A7FB8)
                            )
                        )
                    )
                    .padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = 20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Date and location
                    Column {
                        if (date.isNotEmpty()) {
                            Text(
                                text = date,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                        if (location.isNotEmpty()) {
                            Text(
                                text = location,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }

                    // Temperature and condition
                    Column {
                        Text(
                            text = temperature,
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = condition,
                            fontSize = 18.sp,
                            color = Color.White.copy(alpha = 0.95f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "High:",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Text(
                                    text = "$highTemp°",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Low:",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Text(
                                    text = "$lowTemp°",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        // Overlapping weather icon
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = condition,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterEnd)
                .offset(x = 20.dp, y = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherCardPreview() {
    WeatherCard(
        temperature = "29°C",
        condition = "Partly Cloudy",
        highTemp = "30",
        lowTemp = "18",
        iconRes = R.drawable.ic_partly_cloudy,
        date = "Friday, 21 Nov 25",
        location = "Johannesburg"
    )
}
