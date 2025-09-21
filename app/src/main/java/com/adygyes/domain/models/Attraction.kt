package com.adygyes.domain.models

data class Attraction(
    val id: Long,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val category: Category,
    val photoUrl: String?,
    val rating: Float = 0f,
    val isOfflineAvailable: Boolean = false
)

data class Location(
    val latitude: Double,
    val longitude: Double
)

enum class Category(val displayName: String) {
    NATURE("Природа"),
    CULTURAL("Культура"),
    HISTORICAL("История"),
    ENTERTAINMENT("Развлечения"),
    FOOD("Еда"),
    ACCOMMODATION("Размещение")
}
