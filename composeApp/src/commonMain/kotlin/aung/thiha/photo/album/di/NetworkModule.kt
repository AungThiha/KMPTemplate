package aung.thiha.photo.album.di

import aung.thiha.photo.album.network.HttpClientFactory
import io.ktor.client.*
import org.koin.dsl.module

val networkModule = module {
    factory<HttpClientFactory> {
        HttpClientFactory(
            authenticationStorage = get(),
        )
    }
    factory<HttpClient> {
        get<HttpClientFactory>().createHttpClient()
    }
}