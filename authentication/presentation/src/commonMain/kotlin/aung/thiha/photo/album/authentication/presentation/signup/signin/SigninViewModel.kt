package aung.thiha.photo.album.authentication.presentation.signup.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.authentication.domain.model.SigninInput
import aung.thiha.photo.album.authentication.presentation.signup.navigation.AuthenticationNavigator
import aung.thiha.photo.album.authentication.domain.usecase.isEmailValid
import io.github.aungthiha.snackbar.SnackbarChannel
import io.github.aungthiha.snackbar.SnackbarChannelOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import photoalbum.authentication.presentation.generated.resources.Res
import photoalbum.authentication.presentation.generated.resources.authentication_failed
import photoalbum.authentication.presentation.generated.resources.authentication_invalid_email

private const val EMAIL = "EMAIL"
private const val PASSWORD = "PASSWORD"

class SigninViewModel(
    private val sigin: SuspendOperation<SigninInput, Unit>,
    private val navigator: AuthenticationNavigator,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val snackbarChannel: SnackbarChannel = SnackbarChannel()
) : ViewModel(), SigninScreenListener, SnackbarChannelOwner by snackbarChannel {

    // TODO when user moves to the second input, if the email is invalid, show error. Hide error when user comes back to the input field
    val email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    // TODO when user moves to password input field, show minimum password length
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")

    private val mutableOverlayLoading = MutableStateFlow(false)
    val overlayLoading: StateFlow<Boolean> = mutableOverlayLoading

    override fun onEmailChange(email: String) {
        savedStateHandle[EMAIL] = email
    }

    override fun onPasswordChange(password: String) {
        savedStateHandle[PASSWORD] = password
    }

    override fun onSignupClick() {
        navigator.navigateToSignup()
    }

    override fun onSigninClick() {
        // TODO prevent continuous click

        if (isEmailValid(email.value).not()) {
            showSnackBar(Res.string.authentication_invalid_email)
            return
        }

        viewModelScope.launch {
            showOverlayLoading()
            val result = sigin(SigninInput(email = email.value, password = password.value))
            when (result) {
                is Outcome.Failure<Unit> -> {
                    showSnackBar(Res.string.authentication_failed)
                    hideOverlayLoading()
                }

                is Outcome.Success<Unit> -> {
                    navigator.navigateToPhotoList()
                }
            }
        }
    }

    private fun showOverlayLoading() {
        mutableOverlayLoading.value = true
    }

    private fun hideOverlayLoading() {
        mutableOverlayLoading.value = false
    }

}