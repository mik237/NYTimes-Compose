package me.ibrahim.nytimes.domain.managers

import me.ibrahim.nytimes.domain.models.TopStory

interface SharedPrefsManager {
    suspend fun saveTopStory(topStory: TopStory)
    suspend fun getTopStory(): TopStory?
}