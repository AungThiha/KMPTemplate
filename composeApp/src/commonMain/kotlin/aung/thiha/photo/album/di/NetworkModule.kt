package aung.thiha.photo.album.di

import aung.thiha.photo.album.network.HttpClientFactory
import io.ktor.client.*
import org.koin.dsl.module

val networkModule = module {
    factory<HttpClientFactory> {
        HttpClientFactory(
            authPlugin = get()
        )
    }
    factory<HttpClient> {
        get<HttpClientFactory>().createHttpClient()
    }
}