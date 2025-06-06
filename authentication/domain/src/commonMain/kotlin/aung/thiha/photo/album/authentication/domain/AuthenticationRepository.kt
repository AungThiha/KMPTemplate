package aung.thiha.photo.album.authentication.domain

import aung.thiha.photo.album.authentication.domain.model.SigninInput
import aung.thiha.photo.album.authentication.domain.model.SignupInput
import aung.thiha.operation.SuspendOperation

interface AuthenticationRepository {
    val signin: SuspendOperation<SigninInput, Unit>
    val signup: SuspendOperation<SignupInput, Unit>
    val isTokenValid: SuspendOperation<Unit, Unit>
}