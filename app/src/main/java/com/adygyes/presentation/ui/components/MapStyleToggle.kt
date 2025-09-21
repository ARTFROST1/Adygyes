package com.adygyes.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class MapStyle(val displayName: String) {
    SCHEMA("Схема"),
    SATELLITE("Спутник"),
    HYBRID("Гибрид")
}

@Composable
fun MapStyleToggle(
    currentStyle: MapStyle,
    onStyleChange: (MapStyle) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    FloatingActionButton(
        onClick = { expanded = true },
        modifier = modifier.size(48.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Icon(
            Icons.Default.Settings,
            contentDescription = "Стиль карты",
            modifier = Modifier.size(24.dp)
        )
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            MapStyle.values().forEach { style ->
                DropdownMenuItem(
                    text = { 
                        Text(
                            text = style.displayName,
                            color = if (style == currentStyle) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    },
                    onClick = {
                        onStyleChange(style)
                        expanded = false
                    }
                )
            }
        }
    }
}
