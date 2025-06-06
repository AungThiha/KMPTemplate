package aung.thiha.photo.album.adapter.authentication.di

import aung.thiha.photo.album.adapter.authentication.DefaultAuthenticationNavigator
import aung.thiha.photo.album.authentication.domain.AuthenticationNavigator
import org.koin.dsl.module

val authenticationAppModule = module {
    factory<AuthenticationNavigator> { DefaultAuthenticationNavigator(navigationDispatcher = get()) }
}