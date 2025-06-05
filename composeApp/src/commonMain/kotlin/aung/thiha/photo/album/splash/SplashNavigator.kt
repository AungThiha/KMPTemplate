package aung.thiha.photo.album.splash

import aung.thiha.photo.album.authentication.presentation.signup.navigation.SigninRoute
import aung.thiha.photo.album.navigation.NavigationDispatcher
import aung.thiha.photo.album.photos.presentation.navigation.PhotoListRoute
import kotlinx.coroutines.Job

interface SplashNavigator {
    fun toPhotoList(): Job
    fun toSignin(): Job
}

class DefaultSplashNavigator(
    private val navigationDispatcher: NavigationDispatcher
) : SplashNavigator {
    override fun toPhotoList() = navigationDispatcher.navigate(PhotoListRoute) {
        clearBackStack()
    }

    override fun toSignin() = navigationDispatcher.navigate(SigninRoute) {
        clearBackStack()
    }
}