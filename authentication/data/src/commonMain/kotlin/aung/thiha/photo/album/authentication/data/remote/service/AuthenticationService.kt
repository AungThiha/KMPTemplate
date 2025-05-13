package aung.thiha.photo.album.authentication.data.remote.service

import aung.thiha.photo.album.authentication.data.remote.request.AuthenticationRequest
import aung.thiha.photo.album.authentication.data.remote.response.AuthenticationResponse
import aung.thiha.photo.album.authentication.data.remote.response.TokenCheckResponse
import aung.thiha.photo.album.authentication.data.remote.request.RefreshTokenRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.request.*
import io.ktor.http.*

class AuthenticationService(
    private val httpClient: HttpClient
) : AuthenticationDataSource {

    override suspend fun signin(
        authenticationRequest: AuthenticationRequest
    ): AuthenticationResponse {
        return httpClient.post("api/auth/signin") {
            contentType(ContentType.Application.Json)
            setBody(authenticationRequest)
        }.body()
    }

    override suspend fun signup(
        authenticationRequest: AuthenticationRequest
    ): AuthenticationResponse {
        return httpClient.post("api/auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(authenticationRequest)
        }.body()
    }

    override suspend fun isTokenValid(): TokenCheckResponse {
        val httpResponse = httpClient.get("api/auth/is_token_valid") {
            contentType(ContentType.Application.Json)
        }
        if (httpResponse.status != HttpStatusCode.OK) {
            throw Exception()
        }
        return httpResponse.body()
    }

    suspend fun refreshTokens(refreshTokensParams: RefreshTokensParams, refreshToken: String) = refreshTokensParams.run {
        client.post("api/auth/refreshtoken") {
            contentType(ContentType.Application.Json)
            setBody(RefreshTokenRequest(refreshToken))
            markAsRefreshTokenRequest()
        }
    }
}