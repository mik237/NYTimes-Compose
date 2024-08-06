package me.ibrahim.nytimes.domain.models

sealed class UiState private constructor() {
    data object Success : UiState()
    data class Error(val error: String) : UiState()
    data object Loading : UiState()
    data object Default : UiState()
}