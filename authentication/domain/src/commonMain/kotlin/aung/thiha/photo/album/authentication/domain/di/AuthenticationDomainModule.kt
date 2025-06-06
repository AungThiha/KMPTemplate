package aung.thiha.photo.album.authentication.domain.di

import aung.thiha.photo.album.authentication.domain.usecase.IsSignedIn
import aung.thiha.photo.album.authentication.domain.usecase.Signout
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
            navigator = get(),
        )
    }
}