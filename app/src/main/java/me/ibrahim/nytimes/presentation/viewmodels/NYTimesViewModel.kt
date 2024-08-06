package me.ibrahim.nytimes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.ibrahim.nytimes.data.remote.NetworkResponse
import me.ibrahim.nytimes.domain.models.NYTimesState
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.domain.models.UiState
import me.ibrahim.nytimes.domain.usecases.GetTopStoriesUseCase
import javax.inject.Inject


@HiltViewModel
class NYTimesViewModel @Inject constructor(private val getTopStories: GetTopStoriesUseCase) : ViewModel() {


    private val _state = MutableStateFlow(NYTimesState())
    val state = _state.asStateFlow()

    init {
        fetchTopStories()
    }

    fun fetchTopStories() {
        viewModelScope.launch {
            getTopStories("arts").collect { response ->
                when (response) {
                    is NetworkResponse.Error -> {
                        _state.value = _state.value.copy(uiState = UiState.Error(error = response.error))
                    }

                    is NetworkResponse.Success<*> -> {

                        _state.value = _state.value.copy(
                            topStories = response.data as List<TopStory>,
                            uiState = UiState.Success,
                            sections = response.data
                                .mapNotNull { it.section }
                                .plus("All")
                                .distinct()
                                .reversed()
                        )

                        if (_state.value.selectedStory == null) {
                            _state.value = _state.value.copy(selectedStory = response.data.firstOrNull())
                        }
                        updateFilteredList()
                    }

                    NetworkResponse.Loading -> {
                        _state.value = _state.value.copy(uiState = UiState.Loading)
                    }
                }
            }
        }
    }

    private fun updateFilteredList() {
        val section = _state.value.selectedSection
        if (section.isEmpty() || section == "All") {
            _state.value = _state.value.copy(filteredStories = _state.value.topStories)
        } else {
            _state.value = _state.value.copy(
                filteredStories = _state.value.topStories.filter {
                    it.section != null && it.section.contains(section, ignoreCase = true)
                }
            )
        }
    }

    fun filterTopStories(section: String) {
        _state.value = _state.value.copy(selectedSection = section)
        updateFilteredList()
    }



    fun setSelectedStory(story: TopStory) {
        _state.value = _state.value.copy(selectedStory = story)
    }
}

