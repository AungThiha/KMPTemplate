package aung.thiha.photo.album.authentication.domain.usecase

import aung.thiha.coroutines.AppDispatchers
import aung.thiha.operation.Outcome
import aung.thiha.operation.SuspendOperation
import aung.thiha.session.domain.SessionStorage
import kotlinx.coroutines.withContext

class Signout(
    private val sessionStorage: SessionStorage
) : SuspendOperation<Unit, Unit> {
    override suspend fun invoke(input: Unit): Outcome<Unit> {
        sessionStorage.setAuthenticationSession(null)
        withContext(AppDispatchers.main) {
            AppRestartListener.listener.invoke()
        }
        return Outcome.Success(Unit)
    }
}