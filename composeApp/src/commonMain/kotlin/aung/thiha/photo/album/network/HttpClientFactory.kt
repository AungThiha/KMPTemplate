package aung.thiha.photo.album.network

import aung.thiha.photo.album.authentication.data.remote.response.AuthenticationResponse
import aung.thiha.photo.album.authentication.domain.AuthenticationStorage
import aung.thiha.photo.album.authentication.domain.model.AuthenticationSession
import aung.thiha.photo.album.operation.SuspendOperation
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val authenticationStorage: AuthenticationStorage,
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
            bearer {
                loadTokens {
                    authenticationStorage.getAuthenticationSession()?.let { session ->
                        BearerTokens(session.accessToken, session.refreshToken)
                    }
                }

                refreshTokens {
                    authenticationStorage.getAuthenticationSession()?.let { session ->

                        val httpResponse = client.post("api/auth/refreshtoken") {
                            contentType(ContentType.Application.Json)
                            setBody(RefreshTokenRequest(session.refreshToken))
                            markAsRefreshTokenRequest()
                        }

                        try {
                            
                            if (httpResponse.status != HttpStatusCode.OK) {
                                throw Exception()
                            }

                            with(httpResponse.body<AuthenticationResponse>()) {
                                authenticationStorage.setAuthenticationSession(
                                    AuthenticationSession(
                                        accessToken,
                                        refreshToken,
                                        userId
                                    )
                                )

                               return@refreshTokens BearerTokens(accessToken, refreshToken)
                            }
                        } catch (e: Exception) {
                            signoutProvider().invoke(Unit)
                            return@refreshTokens null
                        }
                    }
                    null
                }
            }
        }
    }

}