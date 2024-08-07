package me.ibrahim.nytimes.domain.usecases

import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.domain.repositories.NYTimesRepository
import javax.inject.Inject

class SaveTopStoryUseCase @Inject constructor(private val nyTimesRepository: NYTimesRepository) {
    suspend operator fun invoke(topStory: TopStory) {
        nyTimesRepository.saveTopStory(topStory = topStory)
    }
}