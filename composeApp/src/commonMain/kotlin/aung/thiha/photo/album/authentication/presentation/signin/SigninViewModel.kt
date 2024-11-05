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
import kotlinx.coroutines.launch

class SigninViewModel(
    private val sigin: SuspendOperation<SigninInput, Unit>,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    /*
    1. Add Loading Screen
    2. Error handling: Show some form of erro to the user. Definitely not popup
    3. Navigate to a blank Photo List Screen

    Move to Signup Screen once all three are done
    */
    private val _signinState = mutableStateOf("nothing")
    val signinState: State<String> = _signinState

    fun updateEmail(email: String) {
        // TODO email validation
        this.email = email
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun signin() {
        viewModelScope.launch(AppDispatchers.io) {
            _signinState.value = "loading"
            val result = sigin(SigninInput(email = email, password = password))
            when (result) {
                is Outcome.Failure<Unit> -> {
                    _signinState.value = "failed"
                }
                is Outcome.Success<Unit> -> {
                    authenticationRepository.getAuthenticationSession().let {
                        _signinState.value = it?.userId ?: "no user id"
                    }
                }
            }
        }
    }
}