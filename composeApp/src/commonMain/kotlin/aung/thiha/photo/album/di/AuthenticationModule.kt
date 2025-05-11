package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.data.AuthenticationRepositoryImpl
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationService
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.usecase.IsSignedIn
import aung.thiha.photo.album.authentication.domain.usecase.Signout
import aung.thiha.photo.album.authentication.presentation.signin.SigninViewModel
import aung.thiha.photo.album.authentication.presentation.signup.SignupViewModel
import org.koin.dsl.module

val authenticationModule = module {
    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            sessionStorage = get(),
            authenticationServiceProvider = lazy(LazyThreadSafetyMode.NONE) { get() },
        )
    }
    single {
        AuthenticationService(
            httpClient = get()
        )
    }
    factory {
        SigninViewModel(
            sigin = get<AuthenticationRepository>().signin,
        )
    }
    factory {
        SignupViewModel(
            sigup = get<AuthenticationRepository>().signup,
        )
    }
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