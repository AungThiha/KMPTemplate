package aung.thiha.photo.album.authentication.data

import aung.thiha.photo.album.AppRestartListener
import aung.thiha.photo.album.authentication.data.remote.request.AuthenticationRequest
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationService
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.AuthenticationStorage
import aung.thiha.photo.album.authentication.domain.model.AuthenticationSession
import aung.thiha.photo.album.authentication.domain.model.SigninInput
import aung.thiha.photo.album.authentication.domain.model.SignupInput
import aung.thiha.photo.album.coroutines.AppDispatchers
import aung.thiha.photo.album.operation.SuspendOperation
import aung.thiha.photo.album.operation.suspendOperation
import kotlinx.coroutines.withContext

class AuthenticationRepositoryImpl(
    private val authenticationStorage: AuthenticationStorage,
    private val authenticationService: AuthenticationService,
) : AuthenticationRepository {

    override val signin: SuspendOperation<SigninInput, Unit> = suspendOperation {
        val result = authenticationService.signin(AuthenticationRequest.fromSigninInput(it))
        with(result) {
            authenticationStorage.setAuthenticationSession(
                AuthenticationSession(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    userId = userId,
                )
            )
        }
    }

    override val signup: SuspendOperation<SignupInput, Unit> = suspendOperation {
        val result = authenticationService.signup(AuthenticationRequest.fromSignupInput(it))
        with(result) {
            authenticationStorage.setAuthenticationSession(
                AuthenticationSession(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    userId = userId,
                )
            )
        }
    }

    override val isTokenValid: SuspendOperation<Unit, Unit> = suspendOperation {
        authenticationService.isTokenValid()
    }

}