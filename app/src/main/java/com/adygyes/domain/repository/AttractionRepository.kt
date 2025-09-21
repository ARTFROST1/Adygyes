package com.adygyes.domain.repository

import com.adygyes.domain.models.Attraction
import com.adygyes.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface AttractionRepository {
    
    fun getAllAttractions(): Flow<List<Attraction>>
    
    suspend fun getAttractionById(id: Long): Attraction?
    
    fun getAttractionsByCategory(category: Category): Flow<List<Attraction>>
    
    fun searchAttractions(query: String): Flow<List<Attraction>>
    
    fun getOfflineAttractions(): Flow<List<Attraction>>
    
    suspend fun insertAttraction(attraction: Attraction)
    
    suspend fun insertAttractions(attractions: List<Attraction>)
    
    suspend fun updateAttraction(attraction: Attraction)
    
    suspend fun deleteAttraction(attraction: Attraction)
    
    suspend fun deleteAllAttractions()
}
