package aung.thiha.photo.album.authentication.data.remote.service

import aung.thiha.photo.album.authentication.data.remote.response.AuthenticationRequest
import aung.thiha.photo.album.authentication.data.remote.response.AuthenticationResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class AuthenticationService {
    private val httpClient = HttpClient {
        defaultRequest {
            host = "192.168.1.34:8080"
            url {
                protocol = URLProtocol.HTTP
            }
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
        /*install(Auth){
                bearer {
                    // TODO get access token and refresh here
                    *//*loadTokens {
                        BearerTokens(accessToken!!, refreshToken!!)
                    }*//*

                    refreshTokens {


                        val refreshTokenInfo = client.submitForm(
                            url = "token",
                            formParameters = Parameters.build {
                                append("refreshToken", "refreshToken")
//                                append("x-access-token", userManager.get().refreshToken ?: "")
                            }
                        ) { markAsRefreshTokenRequest() }.body<AuthenticationResponse>()

                        userManager.connect(
                            SessionData(
                                accessToken = refreshTokenInfo
                                    .accessToken,
                                idToken = refreshTokenInfo.idToken,
                                refreshToken = refreshTokenInfo.refreshToken
                            )
                        )

                        val accessToken = refreshTokenInfo.accessToken
                        val refreshToken = userManager.get().refreshToken

                        BearerTokens(accessToken, refreshToken!!)
                    }
                }
            }*/

    }

    suspend fun signin(
        authenticationRequest: AuthenticationRequest
    ): AuthenticationResponse {
        return httpClient.post("api/auth/signin") {
            contentType(ContentType.Application.Json)
            setBody(authenticationRequest)
        }.body()
    }

    suspend fun signup(
        authenticationRequest: AuthenticationRequest
    ): AuthenticationResponse {
        return httpClient.post("api/auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(authenticationRequest)
        }.body()
    }

}