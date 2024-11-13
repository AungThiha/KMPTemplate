package aung.thiha.photo.album.di

import aung.thiha.photo.album.splash.SplashViewModel
import org.koin.dsl.module

val splashModule = module {
    factory {
        SplashViewModel(
            isSignedIn = get()
        )
    }
}