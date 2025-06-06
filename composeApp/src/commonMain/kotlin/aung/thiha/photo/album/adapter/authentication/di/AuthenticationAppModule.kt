package aung.thiha.photo.album.adapter.authentication.di

import aung.thiha.photo.album.adapter.authentication.DefaultAuthenticationNavigator
import aung.thiha.photo.album.adapter.authentication.createNavigateToSplash
import aung.thiha.photo.album.authentication.domain.usecase.NavigateToSplash
import aung.thiha.photo.album.authentication.presentation.signup.navigation.AuthenticationNavigator
import org.koin.dsl.module

val authenticationAppModule = module {
    factory<AuthenticationNavigator> { DefaultAuthenticationNavigator(navigationDispatcher = get()) }
    factory<NavigateToSplash> {
        createNavigateToSplash(navigationDispatcher = get())
    }
}
