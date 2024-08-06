package me.ibrahim.nytimes.domain.repositories

import kotlinx.coroutines.flow.Flow
import me.ibrahim.nytimes.data.remote.NetworkResponse

interface NYTimesRepository {

    suspend fun getTopStories(type: String): Flow<NetworkResponse>
}