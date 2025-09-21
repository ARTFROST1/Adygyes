package com.adygyes.presentation.ui.screens.map

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adygyes.presentation.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onNavigateToAttraction: (Long) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var currentMapStyle by remember { mutableStateOf(MapStyle.SCHEMA) }
    var selectedAttraction by remember { mutableStateOf<com.adygyes.domain.models.Attraction?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adygyes") },
                actions = {
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Yandex Map with attractions
            YandexMapView(
                attractions = uiState.attractions,
                mapStyle = currentMapStyle,
                onMarkerClick = { attraction ->
                    selectedAttraction = attraction
                }
            )
            
            // Map style toggle (top-right)
            MapStyleToggle(
                currentStyle = currentMapStyle,
                onStyleChange = { newStyle ->
                    currentMapStyle = newStyle
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )
            
            // Map controls (right side)
            MapControls(
                onLocationClick = {
                    // TODO: Implement location functionality
                },
                onZoomIn = {
                    // TODO: Implement zoom in
                },
                onZoomOut = {
                    // TODO: Implement zoom out
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            )
            
            // Search bar overlay (top)
            AttractionSearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                    viewModel.onSearchQueryChanged(query)
                },
                onSearch = {
                    // Search is handled by onQueryChange
                },
                suggestions = if (searchQuery.isNotEmpty()) uiState.attractions else emptyList(),
                onSuggestionClick = { attraction ->
                    selectedAttraction = attraction
                    searchQuery = ""
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            
            // Loading indicator overlay
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Загрузка достопримечательностей...")
                        }
                    }
                }
            }
            
            // Error message overlay
            uiState.errorMessage?.let { error ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Ошибка загрузки",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = error,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.loadAttractions() }
                            ) {
                                Text("Повторить")
                            }
                        }
                    }
                }
            }
            
            // Attraction detail bottom sheet
            selectedAttraction?.let { attraction ->
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                ) {
                    AttractionDetailCard(
                        attraction = attraction,
                        onRouteClick = {
                            // TODO: Implement route planning
                            onNavigateToAttraction(attraction.id)
                        },
                        onDismiss = {
                            selectedAttraction = null
                        }
                    )
                }
            }
        }
    }
}
