package aung.thiha.photo.album.authentication.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    @SerialName("refreshToken")
    val refreshToken: String,
)