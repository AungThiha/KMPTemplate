package aung.thiha.photo.album.authentication.data

import aung.thiha.photo.album.authentication.data.remote.request.AuthenticationRequest
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationService
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.AuthenticationStorage
import aung.thiha.photo.album.authentication.domain.model.AuthenticationSession
import aung.thiha.photo.album.authentication.domain.model.SigninInput
import aung.thiha.photo.album.authentication.domain.model.SignupInput
import aung.thiha.operation.SuspendOperation
import aung.thiha.operation.suspendOperation

class AuthenticationRepositoryImpl(
    private val authenticationStorage: AuthenticationStorage,
    private val authenticationServiceProvider: Lazy<AuthenticationService>,
) : AuthenticationRepository {

    private val authenticationService: AuthenticationService
        get() = authenticationServiceProvider.value

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

    override val getAuthenticationSession: SuspendOperation<Unit, AuthenticationSession?> = suspendOperation {
        authenticationStorage.getAuthenticationSession()
    }

    override val setAuthenticationSession: SuspendOperation<AuthenticationSession?, Unit> = suspendOperation {
        authenticationStorage.setAuthenticationSession(it)
    }

}