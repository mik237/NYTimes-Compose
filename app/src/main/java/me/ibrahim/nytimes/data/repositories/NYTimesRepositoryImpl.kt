package me.ibrahim.nytimes.data.repositories

import kotlinx.coroutines.flow.flow
import me.ibrahim.nytimes.data.remote.NetworkResponse
import me.ibrahim.nytimes.data.remote.api.NYTimesApi
import me.ibrahim.nytimes.domain.repositories.NYTimesRepository
import me.ibrahim.nytimes.domain.utils.DataMapper
import me.ibrahim.nytimes.utils.AppConstants
import javax.inject.Inject

class NYTimesRepositoryImpl @Inject constructor(
    private val nyTimesApi: NYTimesApi,
    private val dataMapper: DataMapper
) : NYTimesRepository {

    override suspend fun getTopStories(type: String) = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = nyTimesApi.getTopStories(type = type, apiKey = AppConstants.API_KEY)
            if (response.isSuccessful) {
                emit(NetworkResponse.Success(data = response.body()))
            } else {
                emit(NetworkResponse.Error(error = ""))
            }
        } catch (e: Exception) {
            emit(NetworkResponse.Error(error = e.localizedMessage ?: ""))
        }

    }
}