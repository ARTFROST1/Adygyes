package com.adygyes.data.repository

import com.adygyes.data.database.dao.AttractionDao
import com.adygyes.data.database.entities.AttractionEntity
import com.adygyes.domain.models.Attraction
import com.adygyes.domain.models.Category
import com.adygyes.domain.repository.AttractionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttractionRepositoryImpl @Inject constructor(
    private val attractionDao: AttractionDao
) : AttractionRepository {
    
    override fun getAllAttractions(): Flow<List<Attraction>> {
        return attractionDao.getAllAttractions().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override suspend fun getAttractionById(id: Long): Attraction? {
        return attractionDao.getAttractionById(id)?.toDomainModel()
    }
    
    override fun getAttractionsByCategory(category: Category): Flow<List<Attraction>> {
        return attractionDao.getAttractionsByCategory(category).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override fun searchAttractions(query: String): Flow<List<Attraction>> {
        return attractionDao.searchAttractions(query).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override fun getOfflineAttractions(): Flow<List<Attraction>> {
        return attractionDao.getOfflineAttractions().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
    
    override suspend fun insertAttraction(attraction: Attraction) {
        attractionDao.insertAttraction(attraction.toEntity())
    }
    
    override suspend fun insertAttractions(attractions: List<Attraction>) {
        attractionDao.insertAttractions(attractions.map { it.toEntity() })
    }
    
    override suspend fun updateAttraction(attraction: Attraction) {
        attractionDao.updateAttraction(attraction.toEntity())
    }
    
    override suspend fun deleteAttraction(attraction: Attraction) {
        attractionDao.deleteAttraction(attraction.toEntity())
    }
    
    override suspend fun deleteAllAttractions() {
        attractionDao.deleteAllAttractions()
    }
}

// Extension functions for mapping between domain and data models
private fun AttractionEntity.toDomainModel(): Attraction {
    return Attraction(
        id = id,
        name = name,
        description = description,
        latitude = latitude,
        longitude = longitude,
        category = category,
        photoUrl = photoUrl,
        rating = rating,
        isOfflineAvailable = isOfflineAvailable
    )
}

private fun Attraction.toEntity(): AttractionEntity {
    return AttractionEntity(
        id = id,
        name = name,
        description = description,
        latitude = latitude,
        longitude = longitude,
        category = category,
        photoUrl = photoUrl,
        rating = rating,
        isOfflineAvailable = isOfflineAvailable
    )
}
