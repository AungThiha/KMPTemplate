package aung.thiha.photo.album.di

import aung.thiha.photo.album.navigation.DefaultNavigationDispatcher
import aung.thiha.photo.album.navigation.NavigationDispatcher
import org.koin.dsl.module

val navigationModule = module {
    // TODO replace this in integration test. Make sure it's scoped to threadlocal
    single<NavigationDispatcher> { DefaultNavigationDispatcher }
}