package aung.thiha.photo.album.authentication.presentation.signup.di

import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.presentation.signup.signin.SigninViewModel
import aung.thiha.photo.album.authentication.presentation.signup.signup.SignupViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authenticationPresentationModule = module {
    viewModel {
        SigninViewModel(
            sigin = get<AuthenticationRepository>().signin,
            navigator = get(),
        )
    }
    viewModel {
        SignupViewModel(
            sigup = get<AuthenticationRepository>().signup,
            navigator = get(),
        )
    }
}
