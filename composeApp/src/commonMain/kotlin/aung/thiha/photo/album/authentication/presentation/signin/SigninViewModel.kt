package aung.thiha.photo.album.authentication.presentation.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.model.SigninInput
import aung.thiha.photo.album.coroutines.AppDispatchers
import aung.thiha.photo.album.operation.Outcome
import aung.thiha.photo.album.operation.SuspendOperation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SigninViewModel(
    private val sigin: SuspendOperation<SigninInput, Unit>,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private val _messages: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val messages: StateFlow<List<String>> = _messages.asStateFlow()

    /*
    3. Navigate to a blank Photo List Screen

    Move to Signup Screen once all three are done
    */
    private val _signinState = mutableStateOf(SigninState.Content)
    val signinState: State<SigninState> = _signinState

    fun updateEmail(email: String) {
        // TODO email validation
        this.email = email
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun signin() {
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
                    authenticationRepository.getAuthenticationSession().let {
                        // TODO redirect to Photo List Screen
                        _messages.update { currentMessags ->
                            currentMessags + "Success"
                        }
                        _signinState.value = SigninState.Content
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