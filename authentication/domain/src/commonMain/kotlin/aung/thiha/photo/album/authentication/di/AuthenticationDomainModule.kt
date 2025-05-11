package aung.thiha.photo.album.authentication.di

import aung.thiha.photo.album.authentication.usecase.IsSignedIn
import aung.thiha.photo.album.authentication.usecase.Signout
import org.koin.dsl.module

val authenticationDomainModule = module {
    factory {
        IsSignedIn(
            authenticationRepository = get(),
            sessionStorage = get(),
        )
    }
    factory {
        Signout(
            sessionStorage = get(),
        )
    }
}