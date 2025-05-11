package aung.thiha.session.domain

import aung.thiha.session.domain.model.Session

interface SessionStorage {
    suspend fun getAuthenticationSession() : Session?
    suspend fun setAuthenticationSession(session: Session?)
}