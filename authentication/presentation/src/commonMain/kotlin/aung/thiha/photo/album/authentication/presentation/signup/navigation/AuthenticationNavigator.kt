package aung.thiha.photo.album.authentication.presentation.signup.navigation

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

interface AuthenticationNavigator {
    fun navigateUpFromSignup() : Deferred<Boolean>
    fun navigateToSignup() : Job
    fun navigateToPhotoList() : Job
}