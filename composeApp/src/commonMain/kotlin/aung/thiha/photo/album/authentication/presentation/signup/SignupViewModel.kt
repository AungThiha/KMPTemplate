package aung.thiha.photo.album.authentication.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.photo.album.authentication.domain.model.SignupInput
import aung.thiha.photo.album.authentication.domain.usecase.isEmailValid
import aung.thiha.photo.album.coroutines.AppDispatchers
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SignupViewModel(
    private val sigup: SuspendOperation<SignupInput, Unit>
) : ViewModel() {

    private val _events = MutableSharedFlow<SignupEvent>()
    val events: SharedFlow<SignupEvent> = _events.asSharedFlow()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set

    private val _messages: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val messages: StateFlow<List<String>> = _messages.asStateFlow()

    private val _signupState = mutableStateOf(SignupState.Content)
    val signupState: State<SignupState> = _signupState

    fun updateEmail(email: String) {
        this.email = email
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun updateConfirmPassword(confirmPassword: String) {
        this.confirmPassword = confirmPassword
    }

    fun signup() {

        if (isEmailValid(email).not()) {
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



            val result = sigup(SignupInput(email = email, password = password))
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