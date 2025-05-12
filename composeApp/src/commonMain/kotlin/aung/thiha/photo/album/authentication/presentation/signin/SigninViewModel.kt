package aung.thiha.photo.album.authentication.presentation.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.design.snackbar.SnackbarChannel
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.authentication.model.SigninInput
import aung.thiha.photo.album.authentication.usecase.isEmailValid
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val EMAIL = "EMAIL"
private const val PASSWORD = "PASSWORD"

class SigninViewModel(
    private val sigin: SuspendOperation<SigninInput, Unit>,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    private val _events = MutableSharedFlow<SigninEvent>()
    val events: SharedFlow<SigninEvent> = _events

    val email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    private val snackbarChannel = SnackbarChannel(viewModelScope)
    val snackbarFlow = snackbarChannel.receiveAsFlow()

    private val _signinState = MutableStateFlow(SigninState.Content)
    val signinState: StateFlow<SigninState> = _signinState

    fun updateEmail(email: String) {
        savedStateHandle[EMAIL] = email
    }

    fun updatePassword(password: String) {
        savedStateHandle[PASSWORD] = password
    }

    fun signin() {
        // TODO prevent continuous click

        if (isEmailValid(email.value).not()) {
            snackbarChannel.showSnackBar("Invalid Email")
            return
        }

        viewModelScope.launch(AppDispatchers.io) {
            _signinState.value = SigninState.OverlayLoading
            val result = sigin(SigninInput(email = email.value, password = password.value))
            when (result) {
                is Outcome.Failure<Unit> -> {
                    snackbarChannel.showSnackBar("Failed")
                    _signinState.value = SigninState.Content
                }

                is Outcome.Success<Unit> -> {
                    _events.emit(SigninEvent.NavigateToPhotoList)
                }
            }
        }
    }

}