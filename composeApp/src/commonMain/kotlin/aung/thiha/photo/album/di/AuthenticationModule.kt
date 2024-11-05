package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.data.AuthenticationRepositoryImpl
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationService
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.presentation.signin.SigninViewModel
import aung.thiha.photo.album.authentication.presentation.signup.SignupViewModel
import aung.thiha.photo.album.storage.NAMED_AUTHENTICATION_PREFERENCE
import org.koin.dsl.module

val authenticationModule = module {
    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(
            dataStore = get(NAMED_AUTHENTICATION_PREFERENCE),
            authenticationService = get(),
        )
    }
    single { AuthenticationService() }
    factory {
        SigninViewModel(
            sigin = get<AuthenticationRepository>().signin,
            authenticationRepository = get(),
        )
    }
    factory {
        SignupViewModel(
            sigup = get<AuthenticationRepository>().signup,
            authenticationRepository = get(),
        )
    }
}