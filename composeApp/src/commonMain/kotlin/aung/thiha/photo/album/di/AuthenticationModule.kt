package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.data.AuthenticationRepositoryImpl
import aung.thiha.photo.album.authentication.data.local.AuthenticationStorageImpl
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationService
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.AuthenticationStorage
import aung.thiha.photo.album.authentication.domain.usecase.IsSignedIn
import aung.thiha.photo.album.authentication.domain.usecase.Signout
import aung.thiha.photo.album.authentication.presentation.signin.SigninViewModel
import aung.thiha.photo.album.authentication.presentation.signup.SignupViewModel
import aung.thiha.photo.album.storage.NAMED_AUTHENTICATION_PREFERENCE
import org.koin.dsl.module

val authenticationModule = module {
    factory<AuthenticationStorage> {
        AuthenticationStorageImpl(
            dataStore = get(NAMED_AUTHENTICATION_PREFERENCE)
        )
    }
    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            authenticationStorage = get(),
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
            authenticationStorage = get(),
        )
    }
    factory {
        Signout(
            authenticationStorage = get(),
        )
    }
}