package aung.thiha.photo.album.authentication.domain.usecase

import aung.thiha.photo.album.AppRestartListener
import aung.thiha.photo.album.authentication.domain.AuthenticationStorage
import aung.thiha.photo.album.coroutines.AppDispatchers
import aung.thiha.photo.album.operation.Outcome
import aung.thiha.photo.album.operation.SuspendOperation
import kotlinx.coroutines.withContext

class Signout(
    private val authenticationStorage: AuthenticationStorage
) : SuspendOperation<Unit, Unit> {
    override suspend fun invoke(input: Unit): Outcome<Unit> {
        authenticationStorage.setAuthenticationSession(null)
        withContext(AppDispatchers.main) {
            AppRestartListener.listener.invoke()
        }
        return Outcome.Success(Unit)
    }
}