package aung.thiha.photo.album.authentication.domain.usecase

import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.operation.FailureType
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.session.domain.SessionStorage

class IsSignedIn(
    private val authenticationRepository: AuthenticationRepository,
    private val sessionStorage: SessionStorage
) : SuspendOperation<Unit, Unit> {
    override suspend fun invoke(input: Unit): Outcome<Unit> {
        if (sessionStorage.getAuthenticationSession() == null) {
            return Outcome.Failure(type = FailureType.GENERAL, e = Exception("no token saved"))
        }
        return authenticationRepository.isTokenValid(Unit)
    }
}