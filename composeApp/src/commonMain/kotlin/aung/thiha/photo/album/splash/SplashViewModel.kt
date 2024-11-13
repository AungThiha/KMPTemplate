package aung.thiha.photo.album.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.photo.album.authentication.domain.usecase.IsSignedIn
import aung.thiha.photo.album.coroutines.AppDispatchers
import aung.thiha.photo.album.operation.Outcome
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val isSignedIn: IsSignedIn,
) : ViewModel() {

    private val _events = MutableSharedFlow<SplashEvent>()
    val events: SharedFlow<SplashEvent> = _events.asSharedFlow()

    fun load() {
        viewModelScope.launch(AppDispatchers.io) {
            when (val result = isSignedIn.invoke(Unit)) {
                is Outcome.Failure<*> -> {
                    _events.emit(SplashEvent.NavigateToSignin)
                }
                is Outcome.Success<*> -> {
                    _events.emit(SplashEvent.NavigateToPhotoList)
                }
            }
        }
    }
}