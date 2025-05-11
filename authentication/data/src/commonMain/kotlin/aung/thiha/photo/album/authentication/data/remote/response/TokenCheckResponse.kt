package aung.thiha.photo.album.authentication.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenCheckResponse(
    @SerialName("message")
    val message: String
)