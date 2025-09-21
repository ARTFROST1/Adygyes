package com.adygyes.domain.usecases

import com.adygyes.domain.models.Attraction
import com.adygyes.domain.models.Category
import com.adygyes.domain.repository.AttractionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAttractionsUseCase @Inject constructor(
    private val repository: AttractionRepository
) {
    operator fun invoke(): Flow<List<Attraction>> {
        return repository.getAllAttractions()
    }
    
    fun getByCategory(category: Category): Flow<List<Attraction>> {
        return repository.getAttractionsByCategory(category)
    }
    
    suspend fun getById(id: Long): Attraction? {
        return repository.getAttractionById(id)
    }
}
