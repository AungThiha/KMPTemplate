package aung.thiha.photo.album.authentication.domain.usecase

import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.AuthenticationStorage
import aung.thiha.operation.FailureType
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation

class IsSignedIn(
    private val authenticationRepository: AuthenticationRepository,
    private val authenticationStorage: AuthenticationStorage
) : SuspendOperation<Unit, Unit> {
    override suspend fun invoke(input: Unit): Outcome<Unit> {
        if (authenticationStorage.getAuthenticationSession() == null) {
            return Outcome.Failure(type = FailureType.GENERAL, e = Exception("no token saved"))
        }
        return authenticationRepository.isTokenValid(Unit)
    }
}