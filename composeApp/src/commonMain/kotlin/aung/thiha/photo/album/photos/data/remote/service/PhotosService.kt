package aung.thiha.photo.album.photos.data.remote.service

import aung.thiha.photo.album.authentication.domain.AuthenticationStorage
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class PhotosService(
    private val httpClient: HttpClient,
    private val authenticationStorage: AuthenticationStorage
) {

    suspend fun justChecking(): HttpResponse {
        return httpClient.get("api/is_token_valid")
    }
}