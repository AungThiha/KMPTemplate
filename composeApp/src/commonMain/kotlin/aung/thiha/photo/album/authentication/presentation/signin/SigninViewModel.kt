package aung.thiha.photo.album.authentication.presentation.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.authentication.model.SigninInput
import aung.thiha.photo.album.authentication.presentation.navigation.AuthenticationNavigator
import aung.thiha.photo.album.authentication.usecase.isEmailValid
import co.touchlab.kermit.Logger
import io.github.aungthiha.snackbar.SnackbarChannel
import io.github.aungthiha.snackbar.SnackbarChannelOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import photoalbum.composeapp.generated.resources.Res
import photoalbum.composeapp.generated.resources.failed
import photoalbum.composeapp.generated.resources.invalid_email

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

    override fun updateEmail(email: String) {
        savedStateHandle[EMAIL] = email
    }

    override fun updatePassword(password: String) {
        savedStateHandle[PASSWORD] = password
    }

    override fun navigateToSignup() {
        Logger.d("onNavigation to signup")
        navigator.navigateToSignup()
    }

    override fun signin() {
        // TODO prevent continuous click

        if (isEmailValid(email.value).not()) {
            showSnackBar(Res.string.invalid_email)
            return
        }

        viewModelScope.launch(AppDispatchers.io) {
            showOverlayLoading()
            val result = sigin(SigninInput(email = email.value, password = password.value))
            when (result) {
                is Outcome.Failure<Unit> -> {
                    showSnackBar(Res.string.failed)
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