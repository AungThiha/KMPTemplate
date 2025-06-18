package aung.thiha.photo.album.authentication.data.remote.service

import aung.thiha.photo.album.authentication.data.remote.request.AuthenticationRequest
import aung.thiha.photo.album.authentication.data.remote.response.AuthenticationResponse
import aung.thiha.photo.album.authentication.data.remote.response.TokenCheckResponse

class FakeAuthenticationDataSource : AuthenticationDataSource {

    val signInRequests = mutableListOf<AuthenticationRequest>()
    val signUpRequests = mutableListOf<AuthenticationRequest>()
    var tokenCheckCallCount = 0

    val signInResponses = ArrayDeque<AuthenticationResponse>()
    val signUpResponses = ArrayDeque<AuthenticationResponse>()
    val tokenCheckResponses = ArrayDeque<TokenCheckResponse>()

    override suspend fun signin(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        signInRequests += authenticationRequest
        return signInResponses.removeFirstOrNull()
            ?: error("No signin response enqueued — possible unexpected extra call")
    }

    override suspend fun signup(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        signUpRequests += authenticationRequest
        return signUpResponses.removeFirstOrNull()
            ?: error("No signup response enqueued — possible unexpected extra call")
    }

    override suspend fun isTokenValid(): TokenCheckResponse {
        tokenCheckCallCount++
        return tokenCheckResponses.removeFirstOrNull()
            ?: error("No token check response enqueued — possible unexpected extra call")
    }
}
