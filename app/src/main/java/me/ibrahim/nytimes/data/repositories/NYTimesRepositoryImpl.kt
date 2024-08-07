package me.ibrahim.nytimes.data.repositories

import kotlinx.coroutines.flow.flow
import me.ibrahim.nytimes.BuildConfig
import me.ibrahim.nytimes.data.remote.NetworkResponse
import me.ibrahim.nytimes.data.remote.api.NYTimesApi
import me.ibrahim.nytimes.data.remote.models.TopStoriesResponse
import me.ibrahim.nytimes.domain.managers.SharedPrefsManager
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.domain.repositories.NYTimesRepository
import me.ibrahim.nytimes.domain.utils.DataMapper
import me.ibrahim.nytimes.domain.utils.NetworkConnection
import me.ibrahim.nytimes.utils.AppConstants
import retrofit2.Response
import javax.inject.Inject


class NYTimesRepositoryImpl @Inject constructor(
    private val nyTimesApi: NYTimesApi,
    private val dataMapper: DataMapper,
    private val sharedPrefsManager: SharedPrefsManager,
    private val networkConnection: NetworkConnection
) : NYTimesRepository {

    override suspend fun getTopStories(type: String) = flow {
        try {
            emit(NetworkResponse.Loading)
            if (networkConnection.isConnected()) {
                val response = nyTimesApi.getTopStories(type = type, apiKey = BuildConfig.API_KEY)
                val parsedResponse = handleApiResponse(response)
                emit(parsedResponse)
            } else {
                emit(NetworkResponse.Error(error = "Connection Error: Please check your internet connection!"))
            }
        } catch (e: Exception) {
            emit(NetworkResponse.Error(error = e.localizedMessage ?: ""))
        }
    }


    override suspend fun saveTopStory(topStory: TopStory) = sharedPrefsManager.saveTopStory(topStory = topStory)


    private fun handleApiResponse(response: Response<TopStoriesResponse>): NetworkResponse {
        return if (response.isSuccessful) {
            val topStories = dataMapper.mapResultToTopStories(response.body()?.results)
            NetworkResponse.Success(data = topStories)
        } else {
            val errorMsg = try {
                /*val jsonErrorObj = JSONObject(response.errorBody()!!.string())
                jsonErrorObj.getJSONObject("fault").getString("faultstring")*/

                //error message can be parsed from response as above commented code
                // or can be a generic message as below.
                // it all depends on requirement that how to handle the error message.
                "${response.code()}: ${response.message()}"
            } catch (e: Exception) {
                e.message ?: "Unknown Error"
            }
            NetworkResponse.Error(error = errorMsg)
        }
    }
}