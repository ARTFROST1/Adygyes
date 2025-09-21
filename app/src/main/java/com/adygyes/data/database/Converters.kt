package com.adygyes.data.database

import androidx.room.TypeConverter
import com.adygyes.domain.models.Category

class Converters {
    
    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }
    
    @TypeConverter
    fun toCategory(categoryName: String): Category {
        return Category.valueOf(categoryName)
    }
}
