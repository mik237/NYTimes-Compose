package me.ibrahim.nytimes.data.managers

import android.content.SharedPreferences
import com.google.gson.Gson
import me.ibrahim.nytimes.domain.managers.SharedPrefsManager
import me.ibrahim.nytimes.domain.models.TopStory

class SharedPrefsManagerImpl(private val sharedPrefs: SharedPreferences, private val gson: Gson) : SharedPrefsManager {

    companion object {
        private const val KEY_TOP_STORY = "keyTopStory"
    }

    override suspend fun saveTopStory(topStory: TopStory) {
        try {
            val topStoryStr = gson.toJson(topStory)
            sharedPrefs.edit().putString(KEY_TOP_STORY, topStoryStr).apply()
        } catch (e: Exception) {
            sharedPrefs.edit().remove(KEY_TOP_STORY).apply()
        }
    }

    override suspend fun getTopStory(): TopStory? {
        val topStoryStr = sharedPrefs.getString(KEY_TOP_STORY, "")
        return try {
            if (topStoryStr.isNullOrEmpty().not()) {
                gson.fromJson(topStoryStr, TopStory::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}