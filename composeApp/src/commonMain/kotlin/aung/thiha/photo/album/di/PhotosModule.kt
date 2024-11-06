package aung.thiha.photo.album.di

import aung.thiha.photo.album.photos.data.remote.service.PhotosService
import org.koin.dsl.module

val photoModule = module {
    factory<PhotosService> {
        PhotosService(
            httpClient = get(),
            authenticationStorage = get()
        )
    }
}