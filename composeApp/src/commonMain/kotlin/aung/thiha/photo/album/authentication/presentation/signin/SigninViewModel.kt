package aung.thiha.photo.album.authentication.presentation.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.photo.album.authentication.model.SigninInput
import aung.thiha.photo.album.authentication.usecase.isEmailValid
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SigninViewModel(
    private val sigin: SuspendOperation<SigninInput, Unit>
) : ViewModel() {

    private val _events = MutableSharedFlow<SigninEvent>()
    val events: SharedFlow<SigninEvent> = _events.asSharedFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private val _messages: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val messages: StateFlow<List<String>> = _messages.asStateFlow()
    private val _signinState = mutableStateOf(SigninState.Content)
    val signinState: State<SigninState> = _signinState

    fun updateEmail(email: String) {
        this.email = email
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun signin() {
        if (isEmailValid(email).not()) {
            _messages.update { currentMessags ->
                currentMessags + "Invalid Email"
            }
            return
        }

        viewModelScope.launch(AppDispatchers.io) {
            _signinState.value = SigninState.OverlayLoading
            val result = sigin(SigninInput(email = email, password = password))
            when (result) {
                is Outcome.Failure<Unit> -> {
                    _messages.update { currentMessags ->
                        currentMessags + "Failed"
                    }
                    _signinState.value = SigninState.Content
                }
                is Outcome.Success<Unit> -> {
                    _events.emit(SigninEvent.NavigateToPhotoList)
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