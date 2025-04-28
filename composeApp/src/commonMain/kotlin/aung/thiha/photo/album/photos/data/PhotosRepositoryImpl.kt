package aung.thiha.photo.album.photos.data

import aung.thiha.operation.SuspendOperation
import aung.thiha.operation.suspendOperation
import aung.thiha.photo.album.photos.data.remote.service.PhotosService
import aung.thiha.photo.album.photos.domain.PhotosRepository
import aung.thiha.photo.album.photos.domain.model.Photo

class PhotosRepositoryImpl(
    private val photosService: PhotosService,
) : PhotosRepository {
    override val photos: SuspendOperation<Unit, List<Photo>> = suspendOperation {
        val result = photosService.photos()
        result.map {
            Photo(id = it.id, url = it.url)
        }
    }
}