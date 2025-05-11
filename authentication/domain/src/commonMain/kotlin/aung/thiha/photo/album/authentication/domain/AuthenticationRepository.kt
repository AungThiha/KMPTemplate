package aung.thiha.photo.album.authentication.domain

import aung.thiha.photo.album.authentication.model.SigninInput
import aung.thiha.photo.album.authentication.model.SignupInput
import aung.thiha.operation.SuspendOperation

interface AuthenticationRepository {
    val signin: SuspendOperation<SigninInput, Unit>
    val signup: SuspendOperation<SignupInput, Unit>
    val isTokenValid: SuspendOperation<Unit, Unit>
}