package aung.thiha.photo.album.authentication.presentation.signup.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.authentication.domain.model.SignupInput
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
import photoalbum.authentication.presentation.generated.resources.authentication_passwords_do_not_match

private const val EMAIL = "EMAIL"
private const val PASSWORD = "PASSWORD"
private const val CONFIRM_PASSWORD = "CONFIRM_PASSWORD"

class SignupViewModel(
    private val sigup: SuspendOperation<SignupInput, Unit>,
    private val navigator: AuthenticationNavigator,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(),
    private val snackbarChannel: SnackbarChannel = SnackbarChannel()
) : ViewModel(), SignupScreenListener, SnackbarChannelOwner by snackbarChannel {

    // TODO when user moves to the second input, if the email is invalid, show error. Hide error when user comes back to the input field
    val email = savedStateHandle.getStateFlow(key = EMAIL, initialValue = "")
    var password = savedStateHandle.getStateFlow(key = PASSWORD, initialValue = "")
    // TODO when user stops typing for a while and if the password and confirm password don't match, show an error. Once they start typing, remove the error
    var confirmPassword = savedStateHandle.getStateFlow(key = CONFIRM_PASSWORD, initialValue = "")

    private val mutableOverlayLoading = MutableStateFlow(false)
    val overlayLoading: StateFlow<Boolean> = mutableOverlayLoading

    override fun onUpClick() {
        navigator.navigateUpFromSignup()
    }

    override fun onEmailChange(email: String) {
        savedStateHandle[EMAIL] = email
    }

    override fun onPasswordChange(password: String) {
        savedStateHandle[PASSWORD] = password
    }

    override fun onConfirmPasswordChange(confirmPassword: String) {
        savedStateHandle[CONFIRM_PASSWORD] = confirmPassword
    }

    override fun onSignupClick() {

        // TODO prevent continuous click

        if (isEmailValid(email.value).not()) {
            showSnackBar(Res.string.authentication_invalid_email)
            return
        }

        if (password.value != confirmPassword.value) {
            showSnackBar(Res.string.authentication_passwords_do_not_match)
            return
        }

        viewModelScope.launch(AppDispatchers.io) {
            showOverlayLoading()

            val result = sigup(SignupInput(email = email.value, password = password.value))
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