package aung.thiha.photo.album.authentication.domain

import aung.thiha.photo.album.authentication.domain.model.AuthenticationSession
import aung.thiha.photo.album.authentication.domain.model.SigninInput
import aung.thiha.photo.album.operation.SuspendOperation

interface AuthenticationRepository {
    suspend fun getAuthenticationSession() : AuthenticationSession?
    suspend fun setAuthenticationSession(authenticationSession: AuthenticationSession?)
    val signin: SuspendOperation<SigninInput, Unit>
}