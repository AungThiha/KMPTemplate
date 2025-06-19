package aung.thiha.photo.album.di

import aung.thiha.photo.album.navigation.DefaultNavigationDispatcher
import aung.thiha.photo.album.navigation.NavigationDispatcher
import org.koin.dsl.module

val navigationModule = module {
    single<NavigationDispatcher> { DefaultNavigationDispatcher() }
}