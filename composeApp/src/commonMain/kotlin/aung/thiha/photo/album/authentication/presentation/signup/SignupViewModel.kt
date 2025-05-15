package aung.thiha.photo.album.authentication.presentation.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.compose.snackbar.SnackbarChannel
import aung.thiha.compose.snackbar.SnackbarChannelOwner
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.authentication.model.SignupInput
import aung.thiha.photo.album.authentication.usecase.isEmailValid
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val EMAIL = "EMAIL"
private const val PASSWORD = "PASSWORD"
private const val CONFIRM_PASSWORD = "CONFIRM_PASSWORD"

class SignupViewModel(
    private val sigup: SuspendOperation<SignupInput, Unit>,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val snackbarChannel: SnackbarChannel = SnackbarChannel()
) : ViewModel(), SnackbarChannelOwner by snackbarChannel {

    private val _events = MutableSharedFlow<SignupEvent>()
    val events: SharedFlow<SignupEvent> = _events

    val email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")
    var confirmPassword = savedStateHandle.getStateFlow(key = CONFIRM_PASSWORD, initialValue = "")

    private val mutableOverlayLoading = MutableStateFlow(false)
    val overlayLoading: StateFlow<Boolean> = mutableOverlayLoading

    fun updateEmail(email: String) {
        savedStateHandle[EMAIL] = email
    }

    fun updatePassword(password: String) {
        savedStateHandle[PASSWORD] = password
    }

    fun updateConfirmPassword(confirmPassword: String) {
        savedStateHandle[CONFIRM_PASSWORD] = confirmPassword
    }

    fun signup() {

        // TODO prevent continuous click

        if (isEmailValid(email.value).not()) {
            showSnackBar("Invalid Email")
            return
        }

        if (password != confirmPassword) {
            showSnackBar("Passwords do not match")
            return
        }

        viewModelScope.launch(AppDispatchers.io) {
            showOverlayLoading()

            val result = sigup(SignupInput(email = email.value, password = password.value))
            when (result) {
                is Outcome.Failure<Unit> -> {
                    showSnackBar("Failed")
                    hideOverlayLoading()
                }
                is Outcome.Success<Unit> -> {
                    _events.emit(SignupEvent.NavigateToPhotoList)
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