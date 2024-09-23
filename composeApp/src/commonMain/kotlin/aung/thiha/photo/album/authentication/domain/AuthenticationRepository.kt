package aung.thiha.photo.album.authentication.domain

import aung.thiha.photo.album.authentication.data.model.AuthenticationToken

interface AuthenticationRepository {
    suspend fun getAuthenticationToken() : AuthenticationToken?
    suspend fun setAuthenticationToken(authenticationToken: AuthenticationToken?)
}