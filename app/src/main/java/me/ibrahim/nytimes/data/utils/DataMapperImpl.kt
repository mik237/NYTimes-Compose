package me.ibrahim.nytimes.data.utils

import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.domain.utils.DataMapper

class DataMapperImpl : DataMapper {
    override fun mapResultToTopStories(): List<TopStory> {
        val topStories = mutableListOf<TopStory>()
        return topStories
    }
}