package me.ibrahim.nytimes.domain.utils

import me.ibrahim.nytimes.data.remote.models.Result
import me.ibrahim.nytimes.domain.models.TopStory

interface DataMapper {

    fun mapResultToTopStories(results: List<Result>?): List<TopStory>

}