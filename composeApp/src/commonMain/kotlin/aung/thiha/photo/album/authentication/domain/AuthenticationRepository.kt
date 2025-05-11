package aung.thiha.photo.album.authentication.domain

import aung.thiha.photo.album.authentication.domain.model.SigninInput
import aung.thiha.photo.album.authentication.domain.model.SignupInput
import aung.thiha.operation.SuspendOperation
import aung.thiha.session.domain.model.Session

interface AuthenticationRepository {
    val signin: SuspendOperation<SigninInput, Unit>
    val signup: SuspendOperation<SignupInput, Unit>
    val isTokenValid: SuspendOperation<Unit, Unit>
    val setSession: SuspendOperation<Session?, Unit>
    val getSession: SuspendOperation<Unit, Session?>
}