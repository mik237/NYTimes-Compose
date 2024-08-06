package me.ibrahim.nytimes.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import me.ibrahim.nytimes.data.remote.models.Multimedia

@Keep
@Parcelize
data class TopStory(
    @SerializedName("title") val title: String?,

    @SerializedName("abstract") val description: String?,

    @SerializedName("byline") val byline: String?,

    @SerializedName("created_date") val createdDate: String?,

    @SerializedName("item_type") val itemType: String?,

    @SerializedName("multimedia") val multimedia: List<Multimedia?>?,

    @SerializedName("caption") val caption: String?,

    @SerializedName("largeImageUrl") val largeImageUrl: String?,

    @SerializedName("smallImageUrl") val smallImageUrl: String?,

    @SerializedName("published_date") val publishedDate: String?,

    @SerializedName("section") val section: String?,

    @SerializedName("short_url") val shortUrl: String?,

    @SerializedName("subsection") val subsection: String?,

    @SerializedName("updated_date") val updatedDate: String?,

    @SerializedName("url") val url: String?
) : Parcelable
