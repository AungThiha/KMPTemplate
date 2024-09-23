package aung.thiha.photo.album.authentication.data.remote.response

import aung.thiha.photo.album.authentication.domain.model.SigninInput
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRequest(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
) {
    companion object {
        fun fromSigninInput(signinInput: SigninInput) = AuthenticationRequest(
            email = signinInput.email,
            password = signinInput.password,
        )
    }
}
