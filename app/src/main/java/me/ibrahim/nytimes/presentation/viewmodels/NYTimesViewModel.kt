package me.ibrahim.nytimes.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.ibrahim.nytimes.Car
import me.ibrahim.nytimes.data.remote.NetworkResponse
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.domain.usecases.GetTopStoriesUseCase
import javax.inject.Inject


@HiltViewModel
class NYTimesViewModel @Inject constructor(private val getTopStories: GetTopStoriesUseCase) : ViewModel() {


    private val _selectedStory = MutableStateFlow<TopStory?>(null)
    val selectedStory: StateFlow<TopStory?> = _selectedStory.asStateFlow()


    private val _topStoriesUiState = MutableStateFlow<UiState>(UiState.Default)
    val topStoriesUiState = _topStoriesUiState.asStateFlow()

    init {
        fetchTopStories()
    }

    private fun fetchTopStories() {
        viewModelScope.launch {
            getTopStories("arts").collect { response ->
                when (response) {
                    is NetworkResponse.Error -> {
                        _topStoriesUiState.value = UiState.Error(error = response.error)
                    }

                    is NetworkResponse.Success<*> -> {
                        _topStoriesUiState.value = UiState.Success(data = response.data as List<TopStory>)
                        /*if (_selectedStory.value == null) {
                            _selectedStory.value = response.data.firstOrNull()
                        }*/
                    }

                    NetworkResponse.Loading -> {
                        _topStoriesUiState.value = UiState.Loading
                    }
                }
            }
        }
    }

    fun setSelectedStory(story: TopStory) {
        _selectedStory.value = story
    }
}

sealed class UiState private constructor() {
    data class Success(val data: List<TopStory>) : UiState()
    data class Error(val error: String) : UiState()
    data object Loading : UiState()
    data object Default : UiState()
}

