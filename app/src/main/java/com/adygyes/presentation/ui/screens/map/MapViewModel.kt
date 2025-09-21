package com.adygyes.presentation.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adygyes.domain.models.Category
import com.adygyes.domain.repository.AttractionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val attractionRepository: AttractionRepository,
    private val initializeDataUseCase: com.adygyes.domain.usecases.InitializeDataUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MapState())
    val uiState: StateFlow<MapState> = _uiState.asStateFlow()
    
    init {
        initializeData()
    }
    
    private fun initializeData() {
        viewModelScope.launch {
            try {
                initializeDataUseCase()
                loadAttractions()
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = "Failed to initialize data: ${e.message}"
                    ) 
                }
            }
        }
    }
    
    fun loadAttractions() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            try {
                attractionRepository.getAllAttractions()
                    .catch { exception ->
                        _uiState.update { 
                            it.copy(
                                isLoading = false, 
                                errorMessage = exception.message ?: "Unknown error occurred"
                            ) 
                        }
                    }
                    .collect { attractions ->
                        _uiState.update { 
                            it.copy(
                                attractions = attractions,
                                isLoading = false,
                                errorMessage = null
                            ) 
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = e.message ?: "Failed to load attractions"
                    ) 
                }
            }
        }
    }
    
    fun onAttractionSelected(attractionId: Long) {
        val attraction = _uiState.value.attractions.find { it.id == attractionId }
        _uiState.update { it.copy(selectedAttraction = attraction) }
    }
    
    fun onCategoryFilterChanged(categories: Set<Category>) {
        _uiState.update { it.copy(selectedCategories = categories) }
    }
    
    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        
        if (query.isNotBlank()) {
            searchAttractions(query)
        } else {
            loadAttractions()
        }
    }
    
    private fun searchAttractions(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                attractionRepository.searchAttractions(query)
                    .catch { exception ->
                        _uiState.update { 
                            it.copy(
                                isLoading = false, 
                                errorMessage = exception.message ?: "Search failed"
                            ) 
                        }
                    }
                    .collect { attractions ->
                        _uiState.update { 
                            it.copy(
                                attractions = attractions,
                                isLoading = false,
                                errorMessage = null
                            ) 
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = e.message ?: "Search failed"
                    ) 
                }
            }
        }
    }
    
    fun onMapReady() {
        _uiState.update { it.copy(isMapReady = true) }
    }
    
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
