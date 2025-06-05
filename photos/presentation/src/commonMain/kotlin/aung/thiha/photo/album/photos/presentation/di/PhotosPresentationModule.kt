package aung.thiha.photo.album.photos.presentation.di

import aung.thiha.photo.album.photos.domain.PhotosRepository
import aung.thiha.photo.album.photos.domain.usecase.Signout
import aung.thiha.photo.album.photos.presentation.overview.PhotoListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val photosPresentationModule = module {
    viewModel {
        PhotoListViewModel(
            _signout = get<Signout>(),
            photos = get<PhotosRepository>().photos
        )
    }
}