package aung.thiha.photo.album.di

import aung.thiha.photo.album.photos.data.PhotosRepositoryImpl
import aung.thiha.photo.album.photos.data.remote.service.PhotosService
import aung.thiha.photo.album.photos.domain.PhotosRepository
import aung.thiha.photo.album.photos.presentation.PhotoListViewModel
import org.koin.dsl.module

val photoModule = module {
    factory<PhotosService> {
        PhotosService(
            httpClient = get()
        )
    }
    factory<PhotosRepository> {
        PhotosRepositoryImpl(
            photosService = get(),
        )
    }
    factory {
        PhotoListViewModel(
            photos = get<PhotosRepository>().photos
        )
    }
}