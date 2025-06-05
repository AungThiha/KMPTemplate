package aung.thiha.photo.album.photos.domain

import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.photos.domain.model.Photo

interface PhotosRepository {
    val photos: SuspendOperation<Unit, List<Photo>>
}