package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.usecase.Signout
import aung.thiha.photo.album.network.HttpClientFactory
import io.ktor.client.*
import org.koin.dsl.module

val networkModule = module {
    factory<HttpClientFactory> {
        val authenticationRepository = get<AuthenticationRepository>()
        HttpClientFactory(
            getAuthenticationSession = authenticationRepository.getAuthenticationSession,
            setAuthenticationSession = authenticationRepository.setAuthenticationSession,
            signoutProvider = {
                get<Signout>()
            }
        )
    }
    factory<HttpClient> {
        get<HttpClientFactory>().createHttpClient()
    }
}