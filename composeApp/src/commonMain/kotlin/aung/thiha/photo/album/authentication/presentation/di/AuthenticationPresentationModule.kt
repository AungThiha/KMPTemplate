package aung.thiha.photo.album.authentication.presentation.di

import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.presentation.signup.signin.SigninViewModel
import aung.thiha.photo.album.authentication.presentation.signup.signup.SignupViewModel
import org.koin.dsl.module

val authenticationPresentationModule = module {
    factory {
        SigninViewModel(
            sigin = get<AuthenticationRepository>().signin,
            navigator = get(),
        )
    }
    factory {
        SignupViewModel(
            sigup = get<AuthenticationRepository>().signup,
            navigator = get(),
        )
    }
}
