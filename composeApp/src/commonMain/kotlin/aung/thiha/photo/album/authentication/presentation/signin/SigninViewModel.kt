package aung.thiha.photo.album.authentication.presentation.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.snackbar.SnackbarChannel
import aung.thiha.snackbar.SnackbarChannelOwner
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
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val snackbarChannel: SnackbarChannel = SnackbarChannel()
) : ViewModel(), SnackbarChannelOwner by snackbarChannel {

    private val _events = MutableSharedFlow<SigninEvent>()
    val events: SharedFlow<SigninEvent> = _events

    // TODO when user moves to the second input, if the email is invalid, show error. Hide error when user comes back to the input field
    val email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    // TODO when user moves to password input field, show minimum password length
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    private val mutableOverlayLoading = MutableStateFlow(false)
    val overlayLoading: StateFlow<Boolean> = mutableOverlayLoading

    fun updateEmail(email: String) {
        savedStateHandle[EMAIL] = email
    }

    fun updatePassword(password: String) {
        savedStateHandle[PASSWORD] = password
    }

    fun signin() {
        // TODO prevent continuous click

        if (isEmailValid(email.value).not()) {
            showSnackBar("Invalid Email")
            return
        }

        viewModelScope.launch(AppDispatchers.io) {
            showOverlayLoading()
            val result = sigin(SigninInput(email = email.value, password = password.value))
            when (result) {
                is Outcome.Failure<Unit> -> {
                    showSnackBar("Failed")
                    hideOverlayLoading()
                }

                is Outcome.Success<Unit> -> {
                    _events.emit(SigninEvent.NavigateToPhotoList)
                }
            }
        }
    }

    private inline fun showOverlayLoading() {
        mutableOverlayLoading.value = true
    }

    private inline fun hideOverlayLoading() {
        mutableOverlayLoading.value = false
    }

}