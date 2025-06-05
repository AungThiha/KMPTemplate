package aung.thiha.photo.album.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.photo.album.authentication.domain.usecase.IsSignedIn
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.operation.Outcome
import kotlinx.coroutines.launch

class SplashViewModel(
    private val isSignedIn: IsSignedIn,
    private val navigator: SplashNavigator,
) : ViewModel() {

    fun load() {
        viewModelScope.launch(AppDispatchers.io) {
            when (val result = isSignedIn.invoke(Unit)) {
                is Outcome.Failure<*> -> {
                    navigator.toSignin()
                }
                is Outcome.Success<*> -> {
                    navigator.toPhotoList()
                }
            }
        }
    }
}