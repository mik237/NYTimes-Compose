package me.ibrahim.nytimes.domain.usecases

import kotlinx.coroutines.flow.Flow
import me.ibrahim.nytimes.data.remote.NetworkResponse
import me.ibrahim.nytimes.domain.repositories.NYTimesRepository
import javax.inject.Inject

class GetTopStoriesUseCase @Inject constructor(private val nyTimesRepository: NYTimesRepository) {
    suspend operator fun invoke(type: String): Flow<NetworkResponse> {
        val topStories = nyTimesRepository.getTopStories(type = type)
        return topStories
    }
}