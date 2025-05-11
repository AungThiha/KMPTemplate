package aung.thiha.photo.album.authentication.domain.model

data class AuthenticationSession(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)