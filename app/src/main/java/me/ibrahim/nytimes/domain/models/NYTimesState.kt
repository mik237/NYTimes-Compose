package me.ibrahim.nytimes.domain.models

data class NYTimesState(
    val topStories: List<TopStory> = emptyList(),
    val filteredStories: List<TopStory> = emptyList(),
    val sections: List<String> = emptyList(),
    val uiState: UiState = UiState.Default,
    val selectedStory: TopStory? = null,
    val searchQuery: String = "",
    val selectedSection: String = "",
)