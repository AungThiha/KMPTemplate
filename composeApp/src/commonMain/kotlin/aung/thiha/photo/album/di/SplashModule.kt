package aung.thiha.photo.album.di

import aung.thiha.photo.album.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel {
        SplashViewModel(
            isSignedIn = get()
        )
    }
}