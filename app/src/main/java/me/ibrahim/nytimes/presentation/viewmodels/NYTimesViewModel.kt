package me.ibrahim.nytimes.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.ibrahim.nytimes.data.remote.NetworkResponse
import me.ibrahim.nytimes.domain.usecases.GetTopStoriesUseCase
import javax.inject.Inject


@HiltViewModel
class NYTimesViewModel @Inject constructor(private val getTopStories: GetTopStoriesUseCase) : ViewModel() {

    fun fetchTopStories() {
        Log.d("Top-Stories", "init")
        viewModelScope.launch {
            getTopStories("home").collect {
                when (it) {
                    is NetworkResponse.Error -> {
                        Log.d("Top-Stories", "Error: ${it.error}")
                    }

                    is NetworkResponse.Success<*> -> {
                        Log.d("Top-Stories", "Success: ${it.data}")
                    }

                    NetworkResponse.Loading -> {

                    }
                }
            }
        }
    }
}