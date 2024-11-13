package aung.thiha.photo.album.di

import aung.thiha.photo.album.authentication.domain.AuthenticationRepository
import aung.thiha.photo.album.authentication.domain.usecase.Signout
import aung.thiha.photo.album.network.HttpClientFactory
import io.ktor.client.*
import org.koin.dsl.module

val networkModule = module {
    factory<HttpClientFactory> {
        HttpClientFactory(
            signoutProvider = {
                get<Signout>()
            },
            authenticationStorage = get(),
        )
    }
    factory<HttpClient> {
        get<HttpClientFactory>().createHttpClient()
    }
}