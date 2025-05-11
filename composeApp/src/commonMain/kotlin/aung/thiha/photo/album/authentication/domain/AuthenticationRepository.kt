package aung.thiha.photo.album.authentication.domain

import aung.thiha.photo.album.authentication.domain.model.SigninInput
import aung.thiha.photo.album.authentication.domain.model.SignupInput
import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.authentication.domain.model.AuthenticationSession

interface AuthenticationRepository {
    val signin: SuspendOperation<SigninInput, Unit>
    val signup: SuspendOperation<SignupInput, Unit>
    val isTokenValid: SuspendOperation<Unit, Unit>
    val setAuthenticationSession: SuspendOperation<AuthenticationSession?, Unit>
    val getAuthenticationSession: SuspendOperation<Unit, AuthenticationSession?>
}