package com.adygyes.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationManager
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.MapKitFactory

/**
 * Utility class for location-related operations
 */
object LocationUtils {
    
    /**
     * Check if location permissions are granted
     */
    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    /**
     * Get user's current location using Yandex LocationManager
     * Uses requestSingleUpdate for a one-time location request
     */
    fun getCurrentLocation(
        context: Context,
        onLocationReceived: (Point) -> Unit,
        onLocationError: (String) -> Unit
    ) {
        if (!hasLocationPermission(context)) {
            onLocationError("Location permissions not granted")
            return
        }
        
        try {
            val locationManager = MapKitFactory.getInstance().createLocationManager()
            locationManager.requestSingleUpdate(object : LocationListener {
                override fun onLocationUpdated(location: Location) {
                    val point = Point(location.position.latitude, location.position.longitude)
                    onLocationReceived(point)
                }
                
                override fun onLocationStatusUpdated(locationStatus: LocationStatus) {
                    when (locationStatus) {
                        LocationStatus.NOT_AVAILABLE -> {
                            onLocationError("Location services not available")
                        }
                        LocationStatus.AVAILABLE -> {
                            // Location is available, waiting for update
                        }
                        LocationStatus.RESET -> {
                            onLocationError("Location services reset")
                        }
                    }
                }
            })
        } catch (e: SecurityException) {
            onLocationError("Security exception: ${e.message}")
        } catch (e: Exception) {
            onLocationError("Failed to get location: ${e.message}")
        }
    }
    
    /**
     * Calculate distance between two points in kilometers
     */
    fun calculateDistance(point1: Point, point2: Point): Double {
        val earthRadius = 6371.0 // Earth's radius in kilometers
        
        val lat1Rad = Math.toRadians(point1.latitude)
        val lat2Rad = Math.toRadians(point2.latitude)
        val deltaLatRad = Math.toRadians(point2.latitude - point1.latitude)
        val deltaLonRad = Math.toRadians(point2.longitude - point1.longitude)
        
        val a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        
        return earthRadius * c
    }
    
    /**
     * Default location for Adygea (Maykop city center)
     */
    fun getDefaultAdygeaLocation(): Point {
        return Point(44.6098, 40.1006) // Maykop coordinates
    }
}
