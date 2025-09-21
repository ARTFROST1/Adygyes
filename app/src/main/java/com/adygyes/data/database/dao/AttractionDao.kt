package com.adygyes.data.database.dao

import androidx.room.*
import com.adygyes.data.database.entities.AttractionEntity
import com.adygyes.domain.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface AttractionDao {
    
    @Query("SELECT * FROM attractions")
    fun getAllAttractions(): Flow<List<AttractionEntity>>
    
    @Query("SELECT * FROM attractions WHERE id = :id")
    suspend fun getAttractionById(id: Long): AttractionEntity?
    
    @Query("SELECT * FROM attractions WHERE category = :category")
    fun getAttractionsByCategory(category: Category): Flow<List<AttractionEntity>>
    
    @Query("SELECT * FROM attractions WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchAttractions(query: String): Flow<List<AttractionEntity>>
    
    @Query("SELECT * FROM attractions WHERE isOfflineAvailable = 1")
    fun getOfflineAttractions(): Flow<List<AttractionEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttraction(attraction: AttractionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttractions(attractions: List<AttractionEntity>)
    
    @Update
    suspend fun updateAttraction(attraction: AttractionEntity)
    
    @Delete
    suspend fun deleteAttraction(attraction: AttractionEntity)
    
    @Query("DELETE FROM attractions")
    suspend fun deleteAllAttractions()
}
