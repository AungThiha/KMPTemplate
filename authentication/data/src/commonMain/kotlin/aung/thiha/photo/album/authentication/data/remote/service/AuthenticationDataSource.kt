package aung.thiha.photo.album.authentication.data.remote.service

import aung.thiha.photo.album.authentication.data.remote.request.AuthenticationRequest
import aung.thiha.photo.album.authentication.data.remote.response.AuthenticationResponse
import aung.thiha.photo.album.authentication.data.remote.response.TokenCheckResponse

interface AuthenticationDataSource {
    suspend fun signin(authenticationRequest: AuthenticationRequest): AuthenticationResponse
    suspend fun signup(authenticationRequest: AuthenticationRequest): AuthenticationResponse
    suspend fun isTokenValid(): TokenCheckResponse
}