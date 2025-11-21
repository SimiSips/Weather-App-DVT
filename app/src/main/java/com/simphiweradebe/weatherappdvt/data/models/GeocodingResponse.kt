package com.simphiweradebe.weatherappdvt.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoLocation(
    @SerialName("name") val name: String? = null,
    @SerialName("lat") val lat: Double? = null,
    @SerialName("lon") val lon: Double? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("state") val state: String? = null
)
