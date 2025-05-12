package aung.thiha.photo.album.authentication.presentation.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.authentication.model.SignupInput
import aung.thiha.photo.album.authentication.usecase.isEmailValid
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val EMAIL = "EMAIL"
private const val PASSWORD = "PASSWORD"
private const val CONFIRM_PASSWORD = "CONFIRM_PASSWORD"

class SignupViewModel(
    private val sigup: SuspendOperation<SignupInput, Unit>,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    private val _events = MutableSharedFlow<SignupEvent>()
    val events: SharedFlow<SignupEvent> = _events

    val email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")
    var confirmPassword = savedStateHandle.getStateFlow(key = CONFIRM_PASSWORD, initialValue = "")

    private val _messages: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val messages: StateFlow<List<String>> = _messages

    private val _signupState = MutableStateFlow(SignupState.Content)
    val signupState: StateFlow<SignupState> = _signupState

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
            _messages.update { currentMessags ->
                currentMessags + "Invalid Email"
            }
            return
        }

        if (password != confirmPassword) {
            _messages.update { currentMessags ->
                currentMessags + "Passwords do not match"
            }
            _signupState.value = SignupState.Content
            return
        }

        viewModelScope.launch(AppDispatchers.io) {
            _signupState.value = SignupState.OverlayLoading

            val result = sigup(SignupInput(email = email.value, password = password.value))
            when (result) {
                is Outcome.Failure<Unit> -> {
                    _messages.update { currentMessags ->
                        currentMessags + "Failed"
                    }
                    _signupState.value = SignupState.Content
                }
                is Outcome.Success<Unit> -> {
                    _events.emit(SignupEvent.NavigateToPhotoList)
                }
            }
        }
    }

    fun setMessageShown(message: String) {
        _messages.update { currentMessages ->
            currentMessages.filter { it != message }
        }
    }
}