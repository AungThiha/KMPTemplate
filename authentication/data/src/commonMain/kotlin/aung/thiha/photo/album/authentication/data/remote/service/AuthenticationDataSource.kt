package aung.thiha.photo.album.authentication.data.remote.service

import aung.thiha.photo.album.authentication.data.remote.request.AuthenticationRequest
import aung.thiha.photo.album.authentication.data.remote.response.AuthenticationResponse
import aung.thiha.photo.album.authentication.data.remote.response.TokenCheckResponse
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.statement.HttpResponse

interface AuthenticationDataSource {
    suspend fun signin(authenticationRequest: AuthenticationRequest): AuthenticationResponse
    suspend fun signup(authenticationRequest: AuthenticationRequest): AuthenticationResponse
    suspend fun isTokenValid(): TokenCheckResponse
    suspend fun refreshTokens(
        refreshTokensParams: RefreshTokensParams,
        refreshToken: String
    ): HttpResponse
}