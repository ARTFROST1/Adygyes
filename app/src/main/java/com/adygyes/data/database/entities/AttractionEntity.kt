package com.adygyes.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adygyes.domain.models.Category

@Entity(tableName = "attractions")
data class AttractionEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val category: Category,
    val photoUrl: String?,
    val rating: Float = 0f,
    val isOfflineAvailable: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
