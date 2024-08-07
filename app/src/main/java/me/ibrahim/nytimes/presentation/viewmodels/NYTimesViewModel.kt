package me.ibrahim.nytimes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _scrollPosition = MutableStateFlow(0)
    val scrollPosition = _scrollPosition.asStateFlow()

    init {
        fetchTopStories()
        startObservingSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun startObservingSearchQuery() {
        searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .onEach {
                updateFilteredList()
            }
            .launchIn(viewModelScope)
    }

    private fun updateFilteredList() {
        val section = state.value.selectedSection
        val query = searchQuery.value
        val filteredBySection = if (section.isEmpty() || section == "All") {
            _state.value.topStories
        } else {
            _state.value.topStories.filter {
                it.section != null && it.section.contains(section, ignoreCase = true)
            }
        }

        val filteredAndSearched = if (query.isEmpty()) {
            filteredBySection
        } else {
            filteredBySection.filter {
                it.title?.contains(query, ignoreCase = true) ?: false || it.byline?.contains(query, ignoreCase = true) ?: false
            }
        }

        _state.value = _state.value.copy(filteredStories = filteredAndSearched)
    }

    private fun fetchTopStories() {
        viewModelScope.launch(Dispatchers.IO) {

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


    //region public functions

    fun onEvent(event: NYTimesEvents) {
        when (event) {
            NYTimesEvents.FetchStories -> fetchTopStories()

            is NYTimesEvents.FilterSection -> {
                _state.value = _state.value.copy(selectedSection = event.section)
                updateFilteredList()
            }

            is NYTimesEvents.SearchStory -> {
                _searchQuery.value = event.query
            }

            is NYTimesEvents.StoryClicked -> {
                _state.value = _state.value.copy(selectedStory = event.story)
            }

            is NYTimesEvents.ScrollPosition -> {
                _scrollPosition.value = event.position
            }
        }
    }

    //endregion public functions
}


sealed class NYTimesEvents {
    data class StoryClicked(val story: TopStory) : NYTimesEvents()
    data class SearchStory(val query: String) : NYTimesEvents()
    data class FilterSection(val section: String) : NYTimesEvents()
    data class ScrollPosition(val position: Int) : NYTimesEvents()
    data object FetchStories : NYTimesEvents()
}

