package me.ibrahim.nytimes.data.remote

sealed class NetworkResponse {
    data class Success<out T>(val data: T) : NetworkResponse()
    data class Error(val error: String) : NetworkResponse()
    data object Loading : NetworkResponse()
}