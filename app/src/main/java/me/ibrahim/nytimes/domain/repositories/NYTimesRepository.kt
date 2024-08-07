package me.ibrahim.nytimes.domain.repositories

import kotlinx.coroutines.flow.Flow
import me.ibrahim.nytimes.data.remote.NetworkResponse
import me.ibrahim.nytimes.domain.models.TopStory

interface NYTimesRepository {

    suspend fun getTopStories(type: String): Flow<NetworkResponse>

    suspend fun saveTopStory(topStory: TopStory)
}