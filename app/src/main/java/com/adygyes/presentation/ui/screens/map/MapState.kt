package com.adygyes.presentation.ui.screens.map

import com.adygyes.domain.models.Attraction
import com.adygyes.domain.models.Category

data class MapState(
    val attractions: List<Attraction> = emptyList(),
    val selectedAttraction: Attraction? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedCategories: Set<Category> = Category.values().toSet(),
    val searchQuery: String = "",
    val isMapReady: Boolean = false,
    val userLocation: com.adygyes.domain.models.Location? = null
)
