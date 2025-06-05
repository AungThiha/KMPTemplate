package aung.thiha.photo.album.photos.data.di

import aung.thiha.photo.album.photos.data.PhotosRepositoryImpl
import aung.thiha.photo.album.photos.data.remote.service.PhotosDataSource
import aung.thiha.photo.album.photos.data.remote.service.PhotosService
import aung.thiha.photo.album.photos.domain.PhotosRepository
import org.koin.dsl.module

val photosDataModule = module {
    single<PhotosDataSource> {
        PhotosService(
            httpClient = get()
        )
    }
    factory<PhotosRepository> {
        PhotosRepositoryImpl(
            photosDataSource = get(),
        )
    }
}