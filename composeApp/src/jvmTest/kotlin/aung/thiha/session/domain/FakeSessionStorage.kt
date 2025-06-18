package aung.thiha.session.domain

import aung.thiha.session.domain.model.Session

class FakeSessionStorage: SessionStorage {
    var session: Session? = null
    override suspend fun getAuthenticationSession(): Session? = session

    override suspend fun setAuthenticationSession(session: Session?) {
        this.session = session
    }
}

