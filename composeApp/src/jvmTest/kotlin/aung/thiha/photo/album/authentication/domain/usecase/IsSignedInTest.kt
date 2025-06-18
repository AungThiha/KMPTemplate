package aung.thiha.photo.album.authentication.domain.usecase

import aung.thiha.operation.Outcome
import aung.thiha.operation.invoke
import aung.thiha.photo.album.authentication.data.remote.response.TokenCheckResponse
import aung.thiha.photo.album.authentication.data.remote.service.FakeAuthenticationDataSource
import aung.thiha.photo.album.di.core.KoinTestExtension
import aung.thiha.session.domain.FakeSessionStorage
import aung.thiha.session.domain.model.Session
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.test.KoinTest
import org.koin.test.inject

@ExtendWith(KoinTestExtension::class)
class IsSignedInTest : KoinTest {

    private val isSignedIn: IsSignedIn by inject()
    private val sessionStorage: FakeSessionStorage by inject()
    private val authDataSource: FakeAuthenticationDataSource by inject()

    @Test
    fun `returns failure when session is null`() = runTest {
        sessionStorage.session = null

        val result = isSignedIn()

        assertTrue(result is Outcome.Failure)
        assertEquals("no token saved", (result as Outcome.Failure).e?.message)
    }

    @Test
    fun `returns success when session exists and token is valid`() = runTest {
        sessionStorage.session = Session("access", "refresh", "user")
        authDataSource.tokenCheckResponses += TokenCheckResponse(message = "all good")

        val result = isSignedIn()

        assertTrue(result is Outcome.Success)
    }

    @Test
    fun `returns failure when token check throws`() = runTest {
        sessionStorage.session = Session("access", "refresh", "user")
        authDataSource.tokenCheckResponses.clear()
        // Don't enqueue anything → simulates error

        val result = isSignedIn()

        assertTrue(result is Outcome.Failure)
        assertEquals("No token check response enqueued — possible unexpected extra call",
            (result as Outcome.Failure).e.message
        )
    }
}

