package com.adygyes.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.adygyes.R
import com.adygyes.domain.models.Attraction
import com.adygyes.domain.models.Category
import com.yandex.mapkit.map.IconStyle
import com.yandex.runtime.image.ImageProvider

object MarkerUtils {
    
    private const val MARKER_SIZE = 120 // Size in pixels
    private const val PHOTO_SIZE = 80 // Photo size inside marker
    private const val BORDER_WIDTH = 6f
    
    /**
     * Creates a custom marker icon for an attraction
     */
    fun createAttractionMarker(
        context: Context,
        attraction: Attraction,
        isSelected: Boolean = false
    ): ImageProvider {
        val bitmap = createMarkerBitmap(context, attraction, isSelected)
        return ImageProvider.fromBitmap(bitmap)
    }
    
    /**
     * Creates the marker bitmap with photo and category styling
     */
    private fun createMarkerBitmap(
        context: Context,
        attraction: Attraction,
        isSelected: Boolean
    ): Bitmap {
        val size = if (isSelected) (MARKER_SIZE * 1.2f).toInt() else MARKER_SIZE
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Draw background circle
        val paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
        
        val centerX = size / 2f
        val centerY = size / 2f
        val radius = size / 2f - BORDER_WIDTH
        
        // Background color based on category
        paint.color = getCategoryColor(attraction.category)
        canvas.drawCircle(centerX, centerY, radius, paint)
        
        // Draw border
        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = BORDER_WIDTH
            color = if (isSelected) Color.parseColor("#FF6200EE") else Color.WHITE
        }
        canvas.drawCircle(centerX, centerY, radius, paint)
        
        // Draw photo or category icon
        if (attraction.photoUrl != null) {
            // TODO: Load actual photo from URL
            // For now, draw category icon
            drawCategoryIcon(context, canvas, attraction.category, centerX, centerY, PHOTO_SIZE.toFloat())
        } else {
            drawCategoryIcon(context, canvas, attraction.category, centerX, centerY, PHOTO_SIZE.toFloat())
        }
        
        return bitmap
    }
    
    /**
     * Draws category icon in the center of the marker
     */
    private fun drawCategoryIcon(
        context: Context,
        canvas: Canvas,
        category: Category,
        centerX: Float,
        centerY: Float,
        iconSize: Float
    ) {
        val iconRes = when (category) {
            Category.NATURE -> R.drawable.ic_nature
            Category.CULTURAL -> R.drawable.ic_cultural
            Category.HISTORICAL -> R.drawable.ic_historical
            Category.ENTERTAINMENT -> R.drawable.ic_entertainment
            Category.FOOD -> R.drawable.ic_food
            Category.ACCOMMODATION -> R.drawable.ic_accommodation
        }
        
        try {
            val drawable = ContextCompat.getDrawable(context, iconRes)
            drawable?.let {
                val iconBitmap = drawableToBitmap(it, iconSize.toInt())
                val left = centerX - iconSize / 2
                val top = centerY - iconSize / 2
                canvas.drawBitmap(iconBitmap, left, top, null)
            }
        } catch (e: Exception) {
            // Fallback: draw simple colored circle
            val paint = Paint().apply {
                isAntiAlias = true
                color = Color.WHITE
                style = Paint.Style.FILL
            }
            canvas.drawCircle(centerX, centerY, iconSize / 3, paint)
        }
    }
    
    /**
     * Gets color associated with attraction category
     */
    private fun getCategoryColor(category: Category): Int {
        return when (category) {
            Category.NATURE -> Color.parseColor("#4CAF50") // Green
            Category.CULTURAL -> Color.parseColor("#2196F3") // Blue
            Category.HISTORICAL -> Color.parseColor("#FF9800") // Orange
            Category.ENTERTAINMENT -> Color.parseColor("#E91E63") // Pink
            Category.FOOD -> Color.parseColor("#FF5722") // Deep Orange
            Category.ACCOMMODATION -> Color.parseColor("#9C27B0") // Purple
        }
    }
    
    /**
     * Converts drawable to bitmap
     */
    private fun drawableToBitmap(drawable: Drawable, size: Int): Bitmap {
        if (drawable is BitmapDrawable) {
            return Bitmap.createScaledBitmap(drawable.bitmap, size, size, true)
        }
        
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, size, size)
        drawable.draw(canvas)
        return bitmap
    }
    
    /**
     * Creates icon style for markers
     */
    fun createIconStyle(isSelected: Boolean = false): IconStyle {
        return IconStyle().apply {
            anchor = PointF(0.5f, 0.5f) // Center anchor
            rotationType = null
            zIndex = if (isSelected) 1f else 0f
            flat = false
            visible = true
            scale = if (isSelected) 1.2f else 1.0f
        }
    }
}
