package com.adygyes.domain.usecases

import android.content.Context
import com.adygyes.domain.models.Attraction
import com.adygyes.domain.models.Category
import com.adygyes.domain.repository.AttractionRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class AttractionJson(
    val id: Long,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val category: String,
    val photoUrl: String?,
    val rating: Float,
    val isOfflineAvailable: Boolean
)

class InitializeDataUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: AttractionRepository,
    private val gson: Gson
) {
    
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        try {
            // Check if data already exists
            val existingAttractions = repository.getAllAttractions()
            
            // Load initial data from JSON file
            val jsonString = context.resources.openRawResource(
                context.resources.getIdentifier("attractions_data", "raw", context.packageName)
            ).bufferedReader().use { it.readText() }
            val attractionJsonList: List<AttractionJson> = gson.fromJson(
                jsonString,
                object : TypeToken<List<AttractionJson>>() {}.type
            )
            
            val attractions = attractionJsonList.map { json ->
                Attraction(
                    id = json.id,
                    name = json.name,
                    description = json.description,
                    latitude = json.latitude,
                    longitude = json.longitude,
                    category = Category.valueOf(json.category),
                    photoUrl = json.photoUrl,
                    rating = json.rating,
                    isOfflineAvailable = json.isOfflineAvailable
                )
            }
            
            repository.insertAttractions(attractions)
        } catch (e: Exception) {
            // Handle error - could log or throw
            throw InitializationException("Failed to initialize data: ${e.message}", e)
        }
    }
}

class InitializationException(message: String, cause: Throwable) : Exception(message, cause)
