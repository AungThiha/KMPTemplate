package aung.thiha.photo.album.authentication.data

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
import aung.thiha.photo.album.restartApp
import co.touchlab.kermit.Logger
import kotlinx.coroutines.withContext
import org.koin.core.context.stopKoin

class AuthenticationRepositoryImpl(
    private val authenticationStorage: AuthenticationStorage,
    private val authenticationService: AuthenticationService,
) : AuthenticationRepository {

    override val signin: SuspendOperation<SigninInput, Unit> = suspendOperation {
        val result = authenticationService.signin(AuthenticationRequest.fromSigninInput(it))
        Logger.d("signin API successful")
        with(result) {
            Logger.d("accessToken from signin: $accessToken")
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
        Logger.d("signup API successful")
        with(result) {
            Logger.d("accessToken from signup API: $accessToken")
            authenticationStorage.setAuthenticationSession(
                AuthenticationSession(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    userId = userId,
                )
            )
        }
    }
    override val signout = suspend {
        authenticationStorage.setAuthenticationSession(null)
        stopKoin()
        withContext(AppDispatchers.main) {
            restartApp()
        }
    }

}