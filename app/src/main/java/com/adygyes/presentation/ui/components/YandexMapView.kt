package com.adygyes.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.adygyes.domain.models.Attraction
import com.adygyes.presentation.ui.components.MapStyle
import com.adygyes.utils.MarkerUtils

@Composable
fun YandexMapView(
    modifier: Modifier = Modifier,
    attractions: List<Attraction> = emptyList(),
    mapStyle: MapStyle = MapStyle.SCHEMA,
    onMarkerClick: (Attraction) -> Unit = {},
    zoomInTrigger: Int = 0, // Увеличивается для zoom in
    zoomOutTrigger: Int = 0, // Увеличивается для zoom out
    onLocationClick: (() -> Unit)? = null,
    initialCameraPosition: CameraPosition = CameraPosition(
        Point(44.6098, 40.1006), // Майкоп - столица Адыгеи
        10.0f, // zoom
        0.0f, // azimuth
        0.0f  // tilt
    )
) {
    val context = LocalContext.current
    var mapView: MapView? by remember { mutableStateOf(null) }
    var mapObjectCollection: MapObjectCollection? by remember { mutableStateOf(null) }
    
    // Функции для управления масштабом
    val zoomIn = {
        mapView?.mapWindow?.map?.let { map ->
            val currentPosition = map.cameraPosition
            val newZoom = (currentPosition.zoom + 1f).coerceAtMost(21f) // Максимальный zoom 21
            val newPosition = CameraPosition(
                currentPosition.target,
                newZoom,
                currentPosition.azimuth,
                currentPosition.tilt
            )
            map.move(newPosition, Animation(Animation.Type.SMOOTH, 0.3f), null)
        }
    }
    
    val zoomOut = {
        mapView?.mapWindow?.map?.let { map ->
            val currentPosition = map.cameraPosition
            val newZoom = (currentPosition.zoom - 1f).coerceAtLeast(0f) // Минимальный zoom 0
            val newPosition = CameraPosition(
                currentPosition.target,
                newZoom,
                currentPosition.azimuth,
                currentPosition.tilt
            )
            map.move(newPosition, Animation(Animation.Type.SMOOTH, 0.3f), null)
        }
    }
    
    // Реагируем на изменения триггеров масштабирования
    LaunchedEffect(zoomInTrigger) {
        if (zoomInTrigger > 0) {
            zoomIn()
        }
    }
    
    LaunchedEffect(zoomOutTrigger) {
        if (zoomOutTrigger > 0) {
            zoomOut()
        }
    }

    DisposableEffect(context) {
        onDispose {
            mapView?.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            MapView(ctx).apply {
                mapView = this
                
                // Start MapKit
                MapKitFactory.getInstance().onStart()
                onStart()
                
                // Set initial camera position
                mapWindow.map.move(
                    initialCameraPosition,
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )
                
                // Get map object collection for markers
                mapObjectCollection = mapWindow.map.mapObjects
            }
        },
        update = { view ->
            // Update map style
            val yandexMapType = when (mapStyle) {
                MapStyle.SCHEMA -> MapType.MAP
                MapStyle.SATELLITE -> MapType.SATELLITE
                MapStyle.HYBRID -> MapType.HYBRID
            }
            view.mapWindow.map.mapType = yandexMapType
            
            // Enable zoom gestures and controls
            view.mapWindow.map.isZoomGesturesEnabled = true
            view.mapWindow.map.isScrollGesturesEnabled = true
            view.mapWindow.map.isTiltGesturesEnabled = true
            view.mapWindow.map.isRotateGesturesEnabled = true
            
            // Update markers when attractions change
            mapObjectCollection?.let { collection ->
                collection.clear()
                
                attractions.forEach { attraction ->
                    val point = Point(attraction.latitude, attraction.longitude)
                    val placemark = collection.addPlacemark(point)
                    
                    // Set custom marker icon
                    try {
                        val markerIcon = MarkerUtils.createAttractionMarker(
                            context = view.context,
                            attraction = attraction,
                            isSelected = false
                        )
                        val iconStyle = MarkerUtils.createIconStyle(isSelected = false)
                        placemark.setIcon(markerIcon, iconStyle)
                    } catch (e: Exception) {
                        // Use default marker if custom marker creation fails
                        e.printStackTrace()
                    }
                    
                    // Set marker click listener
                    placemark.addTapListener { _, _ ->
                        onMarkerClick(attraction)
                        true
                    }
                    
                    // Store attraction reference in userData for later use
                    placemark.userData = attraction
                }
            }
        }
    )
    
    LaunchedEffect(Unit) {
        MapKitFactory.getInstance().onStart()
    }
}
