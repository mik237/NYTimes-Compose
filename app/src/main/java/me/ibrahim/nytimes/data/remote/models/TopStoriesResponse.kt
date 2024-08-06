package me.ibrahim.nytimes.data.remote.models


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TopStoriesResponse(
    @SerializedName("copyright")
    val copyright: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("num_results")
    val numResults: Int?,
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("section")
    val section: String?,
    @SerializedName("status")
    val status: String?
): Parcelable