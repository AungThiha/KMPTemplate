package aung.thiha.photo.album.authentication.data

import aung.thiha.photo.album.authentication.data.remote.request.AuthenticationRequest
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationService
import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.model.SigninInput
import aung.thiha.photo.album.authentication.model.SignupInput
import aung.thiha.session.domain.model.Session
import aung.thiha.operation.SuspendOperation
import aung.thiha.operation.suspendOperation
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationDataSource
import aung.thiha.session.domain.SessionStorage

class AuthenticationRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val authenticationDataSourceProvider: Lazy<AuthenticationDataSource>,
) : AuthenticationRepository {

    private val authenticationDataSource
        get() = authenticationDataSourceProvider.value

    override val signin: SuspendOperation<SigninInput, Unit> = suspendOperation {
        val result = authenticationDataSource.signin(AuthenticationRequest.fromSigninInput(it))
        with(result) {
            sessionStorage.setAuthenticationSession(
                Session(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    userId = userId,
                )
            )
        }
    }

    override val signup: SuspendOperation<SignupInput, Unit> = suspendOperation {
        val result = authenticationDataSource.signup(AuthenticationRequest.fromSignupInput(it))
        with(result) {
            sessionStorage.setAuthenticationSession(
                Session(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    userId = userId,
                )
            )
        }
    }

    override val isTokenValid: SuspendOperation<Unit, Unit> = suspendOperation {
        authenticationDataSource.isTokenValid()
    }
}
