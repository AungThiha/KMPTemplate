package aung.thiha.photo.album.authentication.domain

import aung.thiha.photo.album.authentication.domain.model.AuthenticationSession

interface AuthenticationStorage {
    suspend fun getAuthenticationSession() : AuthenticationSession?
    suspend fun setAuthenticationSession(authenticationSession: AuthenticationSession?)
}