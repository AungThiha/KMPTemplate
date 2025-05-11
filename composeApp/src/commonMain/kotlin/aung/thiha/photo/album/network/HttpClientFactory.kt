package aung.thiha.photo.album.network

import aung.thiha.operation.SuspendOperation
import aung.thiha.operation.getOrNull
import aung.thiha.photo.album.authentication.data.remote.response.AuthenticationResponse
import aung.thiha.photo.album.authentication.domain.model.AuthenticationSession
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthConfig
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val getAuthenticationSession: SuspendOperation<Unit, AuthenticationSession?>,
    private val setAuthenticationSession: SuspendOperation<AuthenticationSession?, Unit>,
    private val signoutProvider: () -> (SuspendOperation<Unit, Unit>),
) {
    fun createHttpClient(): HttpClient = HttpClient {
        defaultRequest {
            host = "quiet-citadel-44720-935ed12c52b6.herokuapp.com" // https://github.com/AungThiha/PhotoAlbumServer
            url {
                protocol = URLProtocol.HTTPS
            }
            // to connect local server. your app needs to be on the same local network as the server
//            host = "192.168.1.34:8080" // IP:PORT of your local server
//            url {
//                protocol = URLProtocol.HTTP
//            }
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }
        install(Auth) {
            authPlugin()
        }
    }

    private fun Auth.authPlugin() {
        bearer {
            loadTokensPlugin()
            refreshTokensPlugin()
        }
    }

    private fun BearerAuthConfig.loadTokensPlugin() {
        loadTokens {
            getAuthenticationSession.getOrNull()?.let { session: AuthenticationSession ->
                BearerTokens(session.accessToken, session.refreshToken)
            }
        }
    }

    private fun BearerAuthConfig.refreshTokensPlugin() {
        refreshTokens {
            val currentSession = getAuthenticationSession.getOrNull()
            if (currentSession != null) {
                refreshTokens(currentSession)
            } else {
                null
            }
        }
    }

    private suspend fun RefreshTokensParams.refreshTokens(currentSession: AuthenticationSession): BearerTokens? {
        val httpResponse = httpsRefreshTokens(currentSession.refreshToken)

        return try {

            if (httpResponse.status != HttpStatusCode.OK) {
                throw Exception()
            }

            with(httpResponse.body<AuthenticationResponse>()) {
                storeAuthenticationSession()
                BearerTokens(accessToken, refreshToken)
            }
        } catch (e: Exception) {
            signoutProvider().invoke(Unit)
            null
        }
    }

    private suspend fun AuthenticationResponse.storeAuthenticationSession() {
        setAuthenticationSession(
            AuthenticationSession(
                accessToken,
                refreshToken,
                userId
            )
        )
    }

    private suspend fun RefreshTokensParams.httpsRefreshTokens(refreshToken: String) = client.post("api/auth/refreshtoken") {
        contentType(ContentType.Application.Json)
        setBody(RefreshTokenRequest(refreshToken))
        markAsRefreshTokenRequest()
    }

}