package aung.thiha.photo.album.authentication.domain.usecase

import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.session.domain.SessionStorage
import kotlinx.coroutines.Job

fun interface NavigateToSplash: () -> Job

class Signout(
    private val sessionStorage: SessionStorage,
    private val navigateToSplash: NavigateToSplash
) : SuspendOperation<Unit, Unit> {
    override suspend fun invoke(input: Unit): Outcome<Unit> {
        sessionStorage.setAuthenticationSession(null)
        navigateToSplash.invoke().join()
        return Outcome.Success(Unit)
    }
}
