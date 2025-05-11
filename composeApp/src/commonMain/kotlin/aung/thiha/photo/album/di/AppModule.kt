package aung.thiha.photo.album.di

import org.koin.core.module.Module

val appModule : List<Module> = listOf(
    platformModule,
    sessionStorageModule,
    networkModule,
    authenticationModule,
    splashModule,
    photosModule,
)