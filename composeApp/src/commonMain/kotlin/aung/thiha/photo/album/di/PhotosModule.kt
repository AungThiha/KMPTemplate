package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.domain.usecase.Signout
import aung.thiha.photo.album.photos.domain.PhotosRepository
import aung.thiha.photo.album.photos.presentation.PhotoListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val photosModule = module {
    viewModel {
        PhotoListViewModel(
            _signout = get<Signout>(),
            photos = get<PhotosRepository>().photos
        )
    }
}