package me.ibrahim.nytimes.data.remote.models


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Multimedia(
    @SerializedName("caption")
    val caption: String?,
    @SerializedName("copyright")
    val copyright: String?,
    @SerializedName("format")
    val format: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("subtype")
    val subtype: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
) : Parcelable