package aung.thiha.photo.album.di

import aung.thiha.photo.album.splash.DefaultSplashNavigator
import aung.thiha.photo.album.splash.SplashNavigator
import aung.thiha.photo.album.splash.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    factory<SplashNavigator> {
        DefaultSplashNavigator(navigationDispatcher = get())
    }
    viewModel {
        SplashViewModel(
            isSignedIn = get(),
            navigator = get()
        )
    }
}