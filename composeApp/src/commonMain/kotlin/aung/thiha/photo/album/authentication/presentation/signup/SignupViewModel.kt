package aung.thiha.photo.album.authentication.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.model.SignupInput
import aung.thiha.photo.album.coroutines.AppDispatchers
import aung.thiha.photo.album.operation.Outcome
import aung.thiha.photo.album.operation.SuspendOperation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val sigup: SuspendOperation<SignupInput, Unit>,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

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
        viewModelScope.launch(AppDispatchers.io) {
            _signupState.value = SignupState.OverlayLoading

            if (password != confirmPassword) {
                _messages.update { currentMessags ->
                    currentMessags + "Passwords do not match"
                }
                _signupState.value = SignupState.Content
                return@launch
            }

            val result = sigup(SignupInput(email = email, password = password))
            when (result) {
                is Outcome.Failure<Unit> -> {
                    _messages.update { currentMessags ->
                        currentMessags + "Failed"
                    }
                    _signupState.value = SignupState.Content
                }
                is Outcome.Success<Unit> -> {
                    authenticationRepository.getAuthenticationSession().let {
                        // TODO redirect to Photo List Screen
                        _messages.update { currentMessags ->
                            currentMessags + "Success"
                        }
                        _signupState.value = SignupState.Content
                    }
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