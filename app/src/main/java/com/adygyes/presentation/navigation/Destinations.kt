package com.adygyes.presentation.navigation

sealed class Destinations(val route: String) {
    object Map : Destinations("map")
    object AttractionDetail : Destinations("attraction_detail/{attractionId}") {
        fun createRoute(attractionId: Long) = "attraction_detail/$attractionId"
    }
    object Search : Destinations("search")
    object Settings : Destinations("settings")
}
