package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.presentation.signup.navigation.AuthenticationNavigator
import aung.thiha.photo.album.navigation.DefaultAuthenticationNavigator
import aung.thiha.photo.album.navigation.DefaultNavigationDispatcher
import aung.thiha.photo.album.navigation.NavigationDispatcher
import org.koin.dsl.module

val navigationModule = module {
    factory<AuthenticationNavigator> { DefaultAuthenticationNavigator(navigationDispatcher = get()) }

    // TODO replace this in integration test. Make sure it's scoped to
    single<NavigationDispatcher> { DefaultNavigationDispatcher }
}