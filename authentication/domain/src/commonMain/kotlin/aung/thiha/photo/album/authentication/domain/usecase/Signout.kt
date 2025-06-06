package aung.thiha.photo.album.authentication.domain.usecase

import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.photo.album.authentication.domain.AuthenticationNavigator
import aung.thiha.session.domain.SessionStorage

class Signout(
    private val sessionStorage: SessionStorage,
    private val navigator: AuthenticationNavigator,
) : SuspendOperation<Unit, Unit> {
    override suspend fun invoke(input: Unit): Outcome<Unit> {
        sessionStorage.setAuthenticationSession(null)
        navigator.navigateToSplash()
        return Outcome.Success(Unit)
    }
}
