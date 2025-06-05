package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.data.di.authenticationDataModule
import aung.thiha.photo.album.authentication.domain.di.authenticationDomainModule
import aung.thiha.photo.album.authentication.presentation.signup.di.authenticationPresentationModule
import aung.thiha.photo.album.photos.data.di.photosDataModule
import aung.thiha.photo.album.photos.di.PhotosAppModule
import aung.thiha.photo.album.photos.presentation.di.photosPresentationModule
import aung.thiha.session.data.di.sessionStorageModule
import aung.thiha.storage.di.storageModule
import org.koin.core.module.Module

val appModule : List<Module> = listOf(

    platformModule,

    storageModule,

    sessionStorageModule,

    networkModule,

    splashModule,

    authenticationDataModule,
    authenticationDomainModule,
    authenticationPresentationModule,

    PhotosAppModule,
    photosDataModule,
    photosPresentationModule,

    navigationModule,
)