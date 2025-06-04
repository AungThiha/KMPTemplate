package aung.thiha.photo.album.network

import aung.thiha.photo.album.authentication.data.plugin.AuthPlugin
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val HOST = "quiet-citadel-44720-935ed12c52b6.herokuapp.com" // https://github.com/AungThiha/PhotoAlbumServer

class HttpClientFactory(
    private val authPlugin: AuthPlugin
) {
    fun createHttpClient(): HttpClient = HttpClient {
        defaultRequest {
            host = HOST
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
            /**
             * The reason AuthPlugin is in authentication module instead of directly implementing the plugin is to enable multiple squad ownership
             * one squad can own the composeApp module and another can own the authentication module
             * This allows two different squads to work in parallel
             * */
            authPlugin.plug(this)
        }
    }

}
