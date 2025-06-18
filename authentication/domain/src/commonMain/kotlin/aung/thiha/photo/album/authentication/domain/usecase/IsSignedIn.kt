package aung.thiha.photo.album.authentication.domain.usecase

import aung.thiha.operation.SuspendOperation
import aung.thiha.operation.rethrowIfFailure
import aung.thiha.operation.suspendOperation
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.session.domain.SessionStorage

class IsSignedIn(
    private val authenticationRepository: AuthenticationRepository,
    private val sessionStorage: SessionStorage
) : SuspendOperation<Unit, Unit> by suspendOperation(
    block = {
        if (sessionStorage.getAuthenticationSession() == null) {
            throw Exception("no token saved")
        }
        authenticationRepository.isTokenValid.invoke(Unit).rethrowIfFailure()
    }
)
