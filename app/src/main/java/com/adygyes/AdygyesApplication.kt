package com.adygyes

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AdygyesApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Yandex MapKit
        MapKitFactory.setApiKey("96a70138-5c3d-4b3e-a9fa-b74e645a6a30")
        MapKitFactory.initialize(this)
    }
}
