package aung.thiha.photo.album.di

import aung.thiha.session.data.di.sessionStorageModule
import aung.thiha.storage.di.storageModule
import org.koin.core.module.Module

val appModule : List<Module> = listOf(
    platformModule,
    storageModule,
    sessionStorageModule,
    networkModule,
    authenticationModule,
    splashModule,
    photosModule,
)