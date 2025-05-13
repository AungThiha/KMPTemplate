package aung.thiha.photo.album.photos.data.remote.service

import aung.thiha.photo.album.photos.data.remote.response.PhotoResponse

interface PhotosDataSource {
    suspend fun photos(): List<PhotoResponse>
}