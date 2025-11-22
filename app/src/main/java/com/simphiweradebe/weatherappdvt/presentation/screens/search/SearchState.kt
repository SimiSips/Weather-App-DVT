package com.simphiweradebe.weatherappdvt.presentation.screens.search

import com.simphiweradebe.weatherappdvt.data.models.GeoLocation

data class SearchState(
    val isLoading: Boolean = false,
    val locations: List<GeoLocation> = emptyList(),
    val error: String = "",
    val searchQuery: String = ""
)
