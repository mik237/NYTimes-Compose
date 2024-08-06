package me.ibrahim.nytimes.domain.utils

import me.ibrahim.nytimes.domain.models.TopStory

interface DataMapper {

    fun mapResultToTopStories(): List<TopStory>

}