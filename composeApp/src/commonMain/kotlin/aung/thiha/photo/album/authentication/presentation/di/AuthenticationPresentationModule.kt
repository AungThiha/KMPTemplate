package aung.thiha.photo.album.authentication.presentation.di

import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.presentation.signin.SigninViewModel
import aung.thiha.photo.album.authentication.presentation.signup.SignupViewModel
import org.koin.dsl.module

val authenticationPresentationModule = module {
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
}
