package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.usecase.Signout
import aung.thiha.photo.album.photos.data.PhotosRepositoryImpl
import aung.thiha.photo.album.photos.data.remote.service.PhotosDataSource
import aung.thiha.photo.album.photos.data.remote.service.PhotosService
import aung.thiha.photo.album.photos.domain.PhotosRepository
import aung.thiha.photo.album.photos.presentation.PhotoListViewModel
import org.koin.dsl.module

val photosModule = module {
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
    factory {
        PhotoListViewModel(
            _signout = get<Signout>(),
            photos = get<PhotosRepository>().photos
        )
    }
}