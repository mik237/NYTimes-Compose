package me.ibrahim.nytimes.data.remote.api

import me.ibrahim.nytimes.data.remote.models.TopStoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYTimesApi {

    @GET("topstories/v2/{type}.json")
    suspend fun getTopStories(
        @Path("type") type: String,
        @Query("api-key") apiKey: String
    ): Response<TopStoriesResponse>
}