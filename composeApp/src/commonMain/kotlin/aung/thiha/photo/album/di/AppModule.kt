package aung.thiha.photo.album.di

import org.koin.core.module.Module

val appModule : List<Module> = listOf(
    platformModule,
    dataStorageModule,
    networkModule,
    authenticationModule,
    photoModule
)