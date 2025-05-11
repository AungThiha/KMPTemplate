package aung.thiha.session.domain.model

data class Session(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)