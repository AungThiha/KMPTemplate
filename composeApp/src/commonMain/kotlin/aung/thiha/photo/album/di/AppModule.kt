package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.data.di.authenticationDataModule
import aung.thiha.photo.album.authentication.di.authenticationDomainModule
import aung.thiha.photo.album.authentication.presentation.di.authenticationPresentationModule
import aung.thiha.session.data.di.sessionStorageModule
import aung.thiha.storage.di.storageModule
import org.koin.core.module.Module

val appModule : List<Module> = listOf(
    platformModule,
    storageModule,
    sessionStorageModule,
    networkModule,
    authenticationDataModule,
    authenticationDomainModule,
    authenticationPresentationModule,
    splashModule,
    photosModule,
)