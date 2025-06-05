package aung.thiha.photo.album.photos.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("url")
    val url: String
)
