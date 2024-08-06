package me.ibrahim.nytimes.data.utils

import me.ibrahim.nytimes.data.remote.models.Result
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.domain.utils.DataMapper

class DataMapperImpl : DataMapper {

    override fun mapResultToTopStories(results: List<Result>?): List<TopStory> {
        return results?.map { result ->
            TopStory(
                title = result.title,
                description = result.abstract,
                byline = result.byline,
                createdDate = result.createdDate,
                itemType = result.itemType,
                caption = result.multimedia?.get(0)?.caption ?: "",
                largeImageUrl = getLargeImageUrl(result),
                smallImageUrl = getSmallImageUrl(result),
                publishedDate = result.publishedDate,
                section = result.section,
                shortUrl = result.shortUrl,
                subsection = result.subsection,
                updatedDate = result.updatedDate,
                url = result.url
            )
        }
            ?: emptyList()
    }

    private fun getLargeImageUrl(result: Result): String {
        val first = result.multimedia?.first { multimedia ->
            val width = multimedia?.width ?: -1
            val format = multimedia?.format ?: ""
            (format.contains("super", ignoreCase = true) || format.contains("jumbo", ignoreCase = true) || width > 1000)
        }

        return first?.url ?: ""
    }

    private fun getSmallImageUrl(result: Result): String {
        val first = result.multimedia?.first { multimedia ->
            val width = multimedia?.width ?: -1
            val format = multimedia?.format ?: ""
            (format.contains("Thumbnail", ignoreCase = true) || width < 300)
        }
        return first?.url ?: ""
    }
}