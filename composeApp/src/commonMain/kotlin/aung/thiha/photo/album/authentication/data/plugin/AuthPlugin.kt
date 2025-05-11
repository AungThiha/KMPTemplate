package aung.thiha.photo.album.authentication.data.plugin

import aung.thiha.operation.SuspendOperation
import aung.thiha.operation.invoke
import aung.thiha.photo.album.authentication.data.remote.response.AuthenticationResponse
import aung.thiha.photo.album.authentication.data.remote.service.AuthenticationService
import aung.thiha.session.domain.SessionStorage
import aung.thiha.session.domain.model.Session
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthConfig
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.http.HttpStatusCode

class AuthPlugin(
    private val sessionStorage: SessionStorage,
    private val authenticationServiceProvider: Lazy<AuthenticationService>,
    private val signoutProvider: Lazy<SuspendOperation<Unit, Unit>>,
) {

    private val signout: SuspendOperation<Unit, Unit>
        get() = signoutProvider.value

    private val authenticationService: AuthenticationService
        get() = authenticationServiceProvider.value

    fun plug(auth: Auth) {
        auth.bearer {
            loadTokensPlugin()
            refreshTokensPlugin()
        }
    }

    private fun BearerAuthConfig.loadTokensPlugin() {
        loadTokens {
            sessionStorage.getAuthenticationSession()?.let { session: Session ->
                BearerTokens(session.accessToken, session.refreshToken)
            }
        }
    }

    private fun BearerAuthConfig.refreshTokensPlugin() {
        refreshTokens {
            val currentSession = sessionStorage.getAuthenticationSession()
            if (currentSession != null) {
                refreshTokens(currentSession)
            } else {
                null
            }
        }
    }

    private suspend fun RefreshTokensParams.refreshTokens(currentSession: Session): BearerTokens? {
        val httpResponse = authenticationService.refreshTokens(this, currentSession.refreshToken)

        return try {

            if (httpResponse.status != HttpStatusCode.OK) {
                throw Exception()
            }

            with(httpResponse.body<AuthenticationResponse>()) {
                storeAuthenticationSession()
                BearerTokens(accessToken, refreshToken)
            }
        } catch (e: Exception) {
            signout.invoke()
            null
        }
    }

    private suspend fun AuthenticationResponse.storeAuthenticationSession() {
        sessionStorage.setAuthenticationSession(
            Session(
                accessToken,
                refreshToken,
                userId
            )
        )
    }
}