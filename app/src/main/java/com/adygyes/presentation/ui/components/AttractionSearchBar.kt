package com.adygyes.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adygyes.domain.models.Attraction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    suggestions: List<Attraction> = emptyList(),
    onSuggestionClick: (Attraction) -> Unit = {},
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
    placeholder: String = "Поиск достопримечательностей...",
    autoFocus: Boolean = false
) {
    var isActive by remember { mutableStateOf(autoFocus) }
    
    // Автоматически активируем поиск при autoFocus
    LaunchedEffect(autoFocus) {
        if (autoFocus) {
            isActive = true
        }
    }
    
    Column(modifier = modifier) {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = { 
                onSearch()
                isActive = false
            },
            active = isActive,
            onActiveChange = { isActive = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Поиск"
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onQueryChange("")
                            isActive = false
                            onDismiss()
                        }
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Очистить"
                        )
                    }
                }
            }
        ) {
            // Search suggestions
            if (suggestions.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(suggestions) { attraction ->
                        AttractionSuggestionItem(
                            attraction = attraction,
                            onClick = {
                                onSuggestionClick(attraction)
                                isActive = false
                            }
                        )
                    }
                }
            } else if (query.isNotEmpty()) {
                // No results message
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ничего не найдено",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun AttractionSuggestionItem(
    attraction: Attraction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category indicator
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(getCategoryColor(attraction.category)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = attraction.category.displayName.first().toString(),
                    color = androidx.compose.ui.graphics.Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = attraction.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = attraction.category.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (attraction.rating > 0) {
                Text(
                    text = "★ ${attraction.rating}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun getCategoryColor(category: com.adygyes.domain.models.Category): androidx.compose.ui.graphics.Color {
    return when (category) {
        com.adygyes.domain.models.Category.NATURE -> androidx.compose.ui.graphics.Color(0xFF4CAF50)
        com.adygyes.domain.models.Category.CULTURAL -> androidx.compose.ui.graphics.Color(0xFF2196F3)
        com.adygyes.domain.models.Category.HISTORICAL -> androidx.compose.ui.graphics.Color(0xFFFF9800)
        com.adygyes.domain.models.Category.ENTERTAINMENT -> androidx.compose.ui.graphics.Color(0xFFE91E63)
        com.adygyes.domain.models.Category.FOOD -> androidx.compose.ui.graphics.Color(0xFFFF5722)
        com.adygyes.domain.models.Category.ACCOMMODATION -> androidx.compose.ui.graphics.Color(0xFF9C27B0)
    }
}

