package com.adygyes.presentation.ui.screens.map

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adygyes.presentation.ui.components.*
import com.adygyes.utils.LocationUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onNavigateToAttraction: (Long) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var selectedAttraction by remember { mutableStateOf<com.adygyes.domain.models.Attraction?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }
    var locationErrorMessage by remember { mutableStateOf<String?>(null) }
    var zoomInTrigger by remember { mutableStateOf(0) }
    var zoomOutTrigger by remember { mutableStateOf(0) }
    
    Scaffold(
        topBar = {
            if (showSearch) {
                // Поисковый режим TopAppBar
                TopAppBar(
                    title = { Text("Поиск достопримечательностей") },
                    navigationIcon = {
                        IconButton(onClick = { 
                            showSearch = false
                            searchQuery = ""
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                        }
                    },
                    actions = {
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            } else {
                // Обычный режим TopAppBar
                TopAppBar(
                    title = { Text("Adygyes") },
                    actions = {
                        IconButton(onClick = { showSearch = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            }
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
                mapStyle = MapStyle.SCHEMA, // Используем только схему
                onMarkerClick = { attraction ->
                    selectedAttraction = attraction
                },
                zoomInTrigger = zoomInTrigger,
                zoomOutTrigger = zoomOutTrigger
            )
            
            // Map controls (right side)
            MapControls(
                onLocationClick = {
                    if (LocationUtils.hasLocationPermission(context)) {
                        LocationUtils.getCurrentLocation(
                            context = context,
                            onLocationReceived = { userLocation ->
                                // TODO: Move map camera to user location
                                // This will be implemented when we add camera control to YandexMapView
                                locationErrorMessage = null
                            },
                            onLocationError = { error ->
                                locationErrorMessage = error
                            }
                        )
                    } else {
                        locationErrorMessage = "Location permissions not granted. Please enable location access in settings."
                    }
                },
                onZoomIn = {
                    zoomInTrigger += 1 // Увеличиваем триггер для zoom in
                },
                onZoomOut = {
                    zoomOutTrigger += 1 // Увеличиваем триггер для zoom out
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            )
            
            // Search bar overlay (top) - показывается только когда showSearch = true
            if (showSearch) {
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
                        showSearch = false // Скрываем поиск после выбора
                    },
                    onDismiss = {
                        showSearch = false // Скрываем поиск при закрытии
                    },
                    autoFocus = true, // Автоматически активируем поиск
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            
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
            
            // Location error message overlay
            locationErrorMessage?.let { error ->
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 80.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = error,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(
                                onClick = { locationErrorMessage = null }
                            ) {
                                Text("OK")
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
