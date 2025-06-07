package aung.thiha.photo.album.adapter.authentication

import aung.thiha.photo.album.authentication.domain.usecase.NavigateToSplash
import aung.thiha.photo.album.navigation.NavigationDispatcher
import aung.thiha.photo.album.splash.SplashRoute

fun createNavigateToSplash(navigationDispatcher: NavigationDispatcher) = NavigateToSplash {
    navigationDispatcher.navigate(destination = SplashRoute, clearBackStack = true)
}
