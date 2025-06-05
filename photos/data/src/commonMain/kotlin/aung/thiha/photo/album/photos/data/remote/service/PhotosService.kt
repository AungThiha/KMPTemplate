package aung.thiha.photo.album.photos.data.remote.service

import aung.thiha.photo.album.photos.data.remote.response.PhotoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PhotosService(
    private val httpClient: HttpClient
) : PhotosDataSource {
    override suspend fun photos(): List<PhotoResponse> {
        return httpClient.get("photos/") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}
