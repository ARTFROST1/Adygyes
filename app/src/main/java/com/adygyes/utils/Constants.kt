package com.adygyes.utils

object Constants {
    // Yandex MapKit
    const val YANDEX_MAPKIT_API_KEY = "YOUR_YANDEX_MAPKIT_API_KEY_HERE"
    
    // Database
    const val DATABASE_NAME = "adygyes_database"
    const val DATABASE_VERSION = 1
    
    // Map settings
    const val DEFAULT_ZOOM = 10f
    const val DEFAULT_LATITUDE = 44.609764 // Майкоп
    const val DEFAULT_LONGITUDE = 40.100516 // Майкоп
    
    // Network
    const val NETWORK_TIMEOUT = 30L
    const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB
    
    // Preferences
    const val PREFERENCES_NAME = "adygyes_preferences"
    const val PREF_THEME_MODE = "theme_mode"
    const val PREF_LANGUAGE = "language"
    const val PREF_MAP_TYPE = "map_type"
    
    // Map types
    enum class MapType {
        VECTOR,
        SATELLITE,
        HYBRID
    }
    
    // Theme modes
    enum class ThemeMode {
        LIGHT,
        DARK,
        SYSTEM
    }
    
    // Languages
    enum class Language(val code: String, val displayName: String) {
        RUSSIAN("ru", "Русский"),
        ENGLISH("en", "English"),
        ADYGHE("ady", "Адыгабзэ")
    }
}
