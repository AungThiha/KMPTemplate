package aung.thiha.photo.album.photos.data.remote.service

import aung.thiha.photo.album.photos.data.remote.response.PhotoResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class PhotosService(
    private val httpClient: HttpClient
) {
    suspend fun photos(): List<PhotoResponse> {
        return httpClient.get("photos/") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}